package gwt.material.design.viki.client.ui;

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

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.viki.client.share.dialog.base.VikiBooleanClientProperty;
import gwt.material.design.viki.client.share.dialog.base.VikiBooleanListener;
import gwt.material.design.viki.client.share.dialog.payment.PaymentDialogInfo;
import gwt.material.design.viki.client.share.dialog.payment.ShowPaymentDialogTask;
import gwt.material.design.viki.client.utils.JsUtils;
import gwt.material.design.viki.client.utils.PropertyRegistrator;

public class VikiDialogPayment extends VikiDialog<ShowPaymentDialogTask, PaymentDialogInfo> {
    // UI Elements
    private Label cashInCaption;
    private Label sumToPayCaption;
    private Label cashInLabel;
    private Label sumToPayLabel;
    private MaterialButton bankPaymentButton;
    private MaterialButton cashPaymentButton;
    private VikiNumpad numpad;
    // Wrappers for layout
    private Div header;
    private Div fastPars;
    private Div numpadWrapper;
    private Div body;
    private Div buttonWrapper;

    public VikiDialogPayment(ShowPaymentDialogTask anEvent) {
        super(anEvent);
    }

    /*
                    <m:MaterialLink iconType="MENU" iconSize="SMALL" addStyleNames="{style.navbutton}"
                                activates="sideBar" circle="true" textAlign="CENTER" float="LEFT"
                                waves="LIGHT" iconColor="white" iconPosition="NONE"/>
    */

    @Override
    protected void construct() {
        PaymentDialogInfo info = task.getInfo();
        header = new Div();
        body = new Div();
        fastPars = new Div();
        buttonWrapper = new Div();
        numpadWrapper = new Div();

        bankPaymentButton =new MaterialButton();
        bankPaymentButton.setIconType(IconType.CREDIT_CARD);
        bankPaymentButton.setIconColor("grey darken-3");
        bankPaymentButton.setIconFontSize(70, Style.Unit.PX);
        bankPaymentButton.setIconSize(IconSize.SMALL);
        bankPaymentButton.setTextAlign(TextAlign.CENTER);
        bankPaymentButton.setFloat(Style.Float.LEFT);
        bankPaymentButton.setIconPosition(IconPosition.NONE);

        cashPaymentButton = new MaterialButton(ButtonType.RAISED, task.getCashPayAction().getInfo().getText(), new MaterialIcon(IconType.BACKSPACE, "black", "white"));

        cashPaymentButton.setCircle(false);
        sumToPayCaption = new Label("К оплате");
        cashInCaption = new Label("Получено");
        sumToPayLabel = new Label(info.getPurchaseSum());
        cashInLabel = new Label("0");
        numpad = new VikiNumpad(cashInLabel.getElement(), entityId);
        fillFastPars(fastPars, info);
        header.add(sumToPayCaption);
        header.add(sumToPayLabel);
        header.add(cashInCaption);
        header.add(cashInLabel);
        buttonWrapper.add(bankPaymentButton);
        buttonWrapper.add(cashPaymentButton);
        numpadWrapper.add(numpad);
        numpadWrapper.add(buttonWrapper);
        body.add(fastPars);
        body.add(numpadWrapper);
        add(header);
        add(body);
    }

    @Override
    protected void registerProperties(ShowPaymentDialogTask anEvent) {
        PropertyRegistrator.registerBooleanProperty(anEvent.getBankPayAction().getEnabledProperty(),
            new VikiBooleanListener() {
                @Override
                public void onChangeValue(boolean value) {
                    bankPaymentButton.setEnabled(value);
                }
            });
        PropertyRegistrator.registerBooleanProperty(anEvent.getCashPayAction().getEnabledProperty(),
            new VikiBooleanListener() {
                @Override
                public void onChangeValue(boolean value) {
                    cashPaymentButton.setEnabled(value);
                }
            });
    }

    @Override
    protected void unregisterProperties(ShowPaymentDialogTask anEvent) {
        VikiBooleanClientProperty enabledProperty = anEvent.getBankPayAction().getEnabledProperty();
        PropertyRegistrator.dispose(enabledProperty);
    }

    private void fillFastPars(Div container, PaymentDialogInfo info) {
        for (int i = 0; i < info.getPars().length; i++) {
            MaterialButton par = new MaterialButton(ButtonType.FLAT, Integer.toString(info.getPars()[i]), null);
            par.setEnabled(info.getParsState()[i]);
            par.addStyleName("par grey lighten-3");
            registerPar(par);
            container.add(par);
        }
    }

    private void registerPar(final MaterialButton par) {
        par.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                JsUtils.sendStringKey(entityId, par.getText());
            }
        });
    }

    @Override
    protected void stylize() {
        setStyleName("viki-dialog-payment");
        body.setStyleName("viki-dialog-payment-body");
        header.setStyleName("viki-dialog-header");
        fastPars.setStyleName("viki-dialog-fast-pars");
        buttonWrapper.setStyleName("viki-dialog-button-wrapper");
        numpadWrapper.setStyleName("viki-dialog-numpad-wrapper");
        bankPaymentButton.addStyleName("viki-dialog-bank-payment z-depth-1 white");
        cashPaymentButton.addStyleName("viki-dialog-cash-payment z-depth-1");
    }
}
