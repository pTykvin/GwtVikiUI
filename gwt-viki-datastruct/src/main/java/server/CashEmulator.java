package server;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.stream.Collectors;

import javax.swing.*;

import client.dialog.payment.PaymentDialogInfo;
import client.dialog.simple.DialogActionInfo;
import client.dialog.simple.SimpleDialogInfo;
import server.data.ProductsDTO;
import server.entities.VikiBooleanProperty;
import server.entities.dialog.KeyboardAccumulator;
import server.entities.dialog.payment.PaymentDialogEntity;
import server.entities.dialog.simple.DialogServerAction;
import server.entities.dialog.simple.SimpleDialogEntity;
import shared.AddPositionToPurchaseEvent;
import shared.ClearPurchaseTask;
import shared.ModifyFocusedContentTask;
import shared.Product;
import shared.PurchasePositionEvent;

public class CashEmulator {
    private static ServerStub server;
    private static JTextArea input = new JTextArea();
    private static VikiBooleanProperty bankEnabledProperty;
    private static VikiBooleanProperty cashEnabledProperty;

    public static void start(ServerStub server) {
        CashEmulator.server = server;
        startUI();
    }

    private static void startUI() {
        JFrame frame = new JFrame();
        JPanel top = new JPanel(new GridLayout(4, 4));
        JButton showAgeDialog = new JButton("Покажи диалог подтверждения возраста");
        JButton showAlertDialog = new JButton("Покажи диалог об ошибке");
        JButton showPaymentDialog = new JButton("Покажи диалог оплаты");
        JButton closeDialog = new JButton("Закрой диалог");
        showAgeDialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                server.showAgeConfirmation(getAgeConfirmation(CashEmulator::pressLeftButton));
            }
        });
        closeDialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                server.closeDialog();
            }
        });
        showAlertDialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                server.showAlertDialog(getAlertDialog());
            }
        });
        showPaymentDialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                server.showPaymentDialog(getPaymentDialog());
            }
        });
        top.add(showAgeDialog);
        top.add(showAlertDialog);
        top.add(showPaymentDialog);
        top.add(closeDialog);
        frame.add(top, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(input);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setTitle("Эмулятор кассы");
        frame.setVisible(true);
    }

    private static void pressCloseButton() {
        eventLog("Нажат крестик");
    }

    private static void pressRightButton() {
        eventLog("Нажата правая кнопка");
    }

    private static void pressLeftButton() {
        eventLog("Нажата левая кнопка");
    }

    public static SimpleDialogEntity getAlertDialog() {
        return getAlertDialog("Ошибка", "Сообщение об ошибке");
    }

    public static SimpleDialogEntity getAlertDialog(String title, String description) {

        SimpleDialogInfo info = new SimpleDialogInfo();
        info.setCaption(title);
        info.setDescription(description);
        info.setStyle("alert");
        info.setImageName("bad");

        SimpleDialogEntity alertEntity = new SimpleDialogEntity(info);
        {
            DialogActionInfo dialogInfo = new DialogActionInfo();
            dialogInfo.setText("Понятно");
            dialogInfo.setButtonSkin(UISkins.BUTTON_WHITE);
            DialogServerAction rightButton = new DialogServerAction(null, dialogInfo, true);
            alertEntity.setRightAction(rightButton);
        }
        DialogServerAction closeAction = new DialogServerAction(CashEmulator::pressCloseButton, null, true);
        alertEntity.setCloseAction(closeAction);
        return alertEntity;
    }

    public static PaymentDialogEntity getPaymentDialog() {
        PaymentDialogInfo info = new PaymentDialogInfo();
        int[] pars = { 50, 100, 500, 1000, 5000 };
        Long sum = calculatePurchaseSum();
        double number = calculatePurchaseSum() / 100D;
        String format = getString(number);
        info.setPurchaseSum(format);
        info.setPars(pars);
        info.setParsState(prepareParsState(pars, sum));
        KeyboardAccumulator accumulator = new KeyboardAccumulator(CashEmulator::updateString);
        PaymentDialogEntity paymentEntity = new PaymentDialogEntity(info, accumulator);
        {
            DialogActionInfo actionInfo = new DialogActionInfo();
            actionInfo.setButtonSkin(UISkins.BACK_PAYMENT_BUTTON);
            DialogServerAction action = new DialogServerAction(CashEmulator::onBankPayment, actionInfo, false);
            bankEnabledProperty = action.enabledProperty();
            paymentEntity.setBankPayment(action);
        }
        {
            DialogActionInfo actionInfo = new DialogActionInfo();
            actionInfo.setButtonSkin(UISkins.BUTTON_BLUE);
            actionInfo.setText("Оплатить");
            DialogServerAction action = new DialogServerAction(CashEmulator::onCashPayment, actionInfo, true);
            action.setEnabled(false);
            cashEnabledProperty = action.enabledProperty();
            paymentEntity.setCashPayment(action);
        }
        paymentEntity.setCloseAction(new DialogServerAction(CashEmulator::closePaymentDialog, null, true));
        return paymentEntity;
    }

    private static void onCashPayment() {

    }

    private static void closePaymentDialog() {
        onClearPurchase();
    }

    private static String getString(double number) {
        refreshProperties(number);
        DecimalFormat formatter;
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator(' ');
        formatter = new DecimalFormat("#,##0", otherSymbols);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        return formatter.format(number);
    }

    private static void updateString(String s) {
        long number = s.isEmpty() ? 0 : Long.parseLong(s);
        refreshProperties(number);
        s = getString(number);
        eventLog(s);
        ModifyFocusedContentTask event = new ModifyFocusedContentTask();
        event.setContent(s);
        server.sendToClient(event);
    }

    private static void refreshProperties(double number) {
        if (bankEnabledProperty != null) {
            bankEnabledProperty.set(number == 0);
        }
        if (cashEnabledProperty != null) {
            cashEnabledProperty.set(number*100 >= calculatePurchaseSum());
        }
    }

    private static void keyEvent(Integer integer) {
        eventLog(Integer.toString(integer));
    }

    public static SimpleDialogEntity getAgeConfirmation(Runnable onConfirm) {
        SimpleDialogInfo info = new SimpleDialogInfo();
        info.setCaption("23.03.1996");
        info.setDescription("Убедитесь, что дата рождения покупателя не позднее");
        info.setImageName("passport");
        info.setStyle("warning");
        SimpleDialogEntity ageConfirmation = new SimpleDialogEntity(info);
        {
            DialogActionInfo actionInfo = new DialogActionInfo();
            actionInfo.setText("Подтвердить");
            actionInfo.setButtonSkin(UISkins.BUTTON_BLUE);
            DialogServerAction leftButton = new DialogServerAction(onConfirm, actionInfo, true);
            ageConfirmation.setLeftAction(leftButton);
        }
        {
            DialogActionInfo actionInfo = new DialogActionInfo();
            actionInfo.setText("Отмена");
            actionInfo.setButtonSkin(UISkins.BUTTON_WHITE);
            DialogServerAction rightButton = new DialogServerAction(CashEmulator::pressRightButton, actionInfo, true);
            ageConfirmation.setRightAction(rightButton);
        }
        DialogServerAction closeAction = new DialogServerAction(CashEmulator::pressCloseButton, null, true);
        ageConfirmation.setCloseAction(closeAction);
        return ageConfirmation;
    }

    public static void eventLog(String text) {
        input.append(text + "\n");
    }

    private static void onBankPayment() {

    }

    public static void tileClick(String item) {
        if ("6".equals(item)) {
            server.showAgeConfirmation(getAgeConfirmation(() -> addPosition(item)));
        } else if ("5".equals(item)) {
            server.showAlertDialog(getAlertDialog());
        } else {
            addPosition(item);
        }
    }

    private static void addPosition(String item) {
        CashEmulator.eventLog("Нажата плитка c кодом " + item);
        Product product = ProductsDTO.instance.get(item);
        product.incrementCount();
        PurchasePositionEvent event;
//        if (product.getCount() == 1) {
            event = new AddPositionToPurchaseEvent();
//        } else {
//            event = new ChangePurchasePositionEvent();
//        }
        event.setCount(product.getCount());
        event.setPrice(product.getPrice() * product.getCount());
        event.setName(product.getProduct());
        event.setItem(item);
        event.setPurchaseSum(calculatePurchaseSum());
        server.sendToClient(event);
    }

    private static Long calculatePurchaseSum() {
        return ProductsDTO.instance.getAll().stream().collect(Collectors.summingLong(p -> p.getPrice() * p.getCount()));
    }

    private static boolean[] prepareParsState(int[] pars, long sum) {
        boolean[] parsState = new boolean[pars.length];
        for (int i = 0; i < pars.length; i++) {
            parsState[i] = (pars[i] * 100 >= sum);
        }
        return parsState;
    }

    public static void onClearPurchase() {
        ProductsDTO.instance.init();
        server.sendToClient(new ClearPurchaseTask());
    }

    public static void subtotal() {
        server.showPaymentDialog(getPaymentDialog());
    }

    public static void onBarcodeEvent(String s) {
        server.showAlertDialog(getAlertDialog(s, "Просканирован штрихкод"));
    }

    public static void onBarcodeMSR(String s, String s1) {
        server.showAlertDialog(getAlertDialog(s, "Прокатана карта"));
    }
}
