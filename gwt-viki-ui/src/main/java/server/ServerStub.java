package server;

import java.util.Map;
import java.util.concurrent.Executors;

import client.place.NameTokens;
import client.rpc.ServerEventBusService;
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.service.RemoteEventServiceServlet;
import gwt.material.design.viki.client.share.dialog.base.VikiBooleanClientProperty;
import server.data.ProductsDTO;
import server.data.TilesDTO;
import server.entities.Entity;
import server.entities.PropertyHandler;
import server.entities.dialog.payment.PaymentDialogEntity;
import server.entities.dialog.simple.SimpleDialogEntity;
import server.keyboard.KeyCode;
import server.keyboard.VikiKeyboard;
import shared.ChangeModeTask;
import shared.CloseDialogTask;
import shared.Product;

public class ServerStub extends RemoteEventServiceServlet implements ServerEventBusService {
    /**
     *
     */

//    private BarcodeEventHandler barcodeEventHandler = new BarcodeEventHandlerImpl(100L);
    private static final long serialVersionUID = 1L;
    private Entity currentDialogEntity;
    private VikiKeyboard keyboard;

    {
        CashEmulator.start(this);
        PropertyHandler.start(this);
//        barcodeEventHandler.onBarcodeHandling(CashEmulator::onBarcodeEvent);
        initKeyboard();
    }

    private void initKeyboard() {
        keyboard = new VikiKeyboard();
        keyboard.start();
        keyboard.onBarcode(CashEmulator::onBarcodeEvent);
        keyboard.onMSR(CashEmulator::onBarcodeMSR);
//        keyboard.onShortlyPressed(EventManager::onKeyShortlyPressed);
//        keyboard.onLongPressed(EventManager::onKeyLongPressed);

//                ui.onKeyEvent(kbd::onRawKeyEvent);

    }

    @Override
    public void start() {
        ProductsDTO.instance.init();
        Executors.newCachedThreadPool().execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendToClient(new ChangeModeTask(NameTokens.sale));
        });
    }

    void closeDialog() {
        sendToClient(new CloseDialogTask());
    }

    void showAgeConfirmation(SimpleDialogEntity ageConfirmation) {
        if (currentDialogEntity != null) {
            currentDialogEntity.doSuicide();
        }
        currentDialogEntity = ageConfirmation;
        sendToClient(ageConfirmation.getEvent());
    }

    @Override
    public void onTileClickEvent(String item) {
        CashEmulator.tileClick(item);
    }

    @Override
    public void onSubTotal() {
        CashEmulator.subtotal();
    }

    @Override
    public void clearPurchase() {
        CashEmulator.onClearPurchase();
    }

    @Override
    public Map<Integer, Product> getTiles() {
        return TilesDTO.instance.getTiles();
    }

    @Override
    public void onDialogEvent(int entityId, int eventId) {
        Entity.handleAction(entityId, eventId);
    }

    @Override
    public void dialogException(String message) {
        CashEmulator.eventLog(message);
    }

    @Override
    public void onSendStringEvent(int entityId, String key) {
        Entity.onSendStringEvent(entityId, key);
    }

    @Override
    public void onSendTouchKeyEvent(int entityId, int key) {
        Entity.onSendKeyEvent(entityId, key);
    }

    @Override
    public void onSendKeyEvent(int keyCode, long number) {
//        System.out.println(("keyCode = " + ((char) keyCode) + "; number = " + number));
        keyboard.onRawKeyEvent(new KeyCode(((char) keyCode), number));
//        barcodeEventHandler.keyboardEvent(String.valueOf((char) keyCode));
    }

    void sendToClient(Event event) {
        addEvent(ServerEventBusService.MODE_SELECTOR_DOMAIN, event);
    }

    public void showAlertDialog(SimpleDialogEntity alertDialog) {
        sendToClient(alertDialog.getEvent());
    }
    public void showPaymentDialog(PaymentDialogEntity paymentDialog) {
        sendToClient(paymentDialog.getEvent());
    }

    public void fireBooleanProperty(VikiBooleanClientProperty property) {
        sendToClient(property);
    }
}