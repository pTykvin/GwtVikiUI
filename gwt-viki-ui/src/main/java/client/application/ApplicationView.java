package client.application;

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


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import client.VoidAsyncCallback;
import client.rpc.ServerEventBusService;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.html.Header;
import client.dialog.payment.ShowPaymentDialogTask;
import client.dialog.simple.ShowSimpleDialogTask;
import gwt.material.design.viki.client.ui.VikiDialogPayment;
import gwt.material.design.viki.client.ui.VikiDialogSimple;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    @UiField
    Header header;
    @UiField
    MaterialPanel main;
    @UiField
    MaterialLink modeselector;
    @UiField
    HTMLPanel dialogWrapper;
//    @UiField
//    VikiKeyboard keyboard;
    private static boolean registred;
    private static long counter;

    Logger logger = Logger.getLogger("NameOfYourLogger");

    @Override
    public void setPageTitle(String title, String description) {

    }

    @Override
    public void setMode(String mode) {
        modeselector.setTargetHistoryToken(mode);
        clickElement(modeselector.getElement());
    }

    @Override
    public void showSimpleDialog(ShowSimpleDialogTask anEvent) {
        showDialog(new VikiDialogSimple(anEvent));
    }

    @Override
    public void showPaymentDialog(ShowPaymentDialogTask anEvent) {
        showDialog(new VikiDialogPayment(anEvent));
    }

    private void showDialog(Widget content) {
        if (dialogWrapper.getWidgetCount() != 0) {
            dialogWrapper.clear();
        }
        dialogWrapper.add(content);
        dialogWrapper.setVisible(true);
    }

    @Override
    public void closeDialog() {
        dialogWrapper.setVisible(false);
        dialogWrapper.clear();
    }

    public void sendStringKeyHandler(int entityId, String key) {
        ServerEventBusService.App.getInstance().onSendStringEvent(entityId, key, new VoidAsyncCallback());
    }

    public void sendKeyCode(int entityId, int key) {
        ServerEventBusService.App.getInstance().onSendTouchKeyEvent(entityId, key, new VoidAsyncCallback());
    }

    public void dialogEventHandler(int entityId, int eventId) {
        ServerEventBusService.App.getInstance().onDialogEvent(entityId, eventId, new VoidAsyncCallback());
    }

    private native void addEventListener(Element elem) /*-{
        var obj = this;
        $wnd.dialogEvent = $entry(function (entityId, eventId)
        {
            obj.@client.application.ApplicationView::dialogEventHandler(II)(entityId, eventId);
        });

        $wnd.closeVikiDialog = $entry(function ()
        {
            obj.@client.application.ApplicationView::closeDialog()();
        });

        $wnd.sendStringKey = $entry(function (entityId, string)
        {
            obj.@client.application.ApplicationView::sendStringKeyHandler(ILjava/lang/String;)(entityId, string);
        });

        $wnd.sendKeyCode = $entry(function (entityId, keyCode)
        {
            obj.@client.application.ApplicationView::sendKeyCode(II)(entityId, keyCode);
        });

    }-*/;

    public static native void clickElement(Element elem) /*-{
        elem.click();
    }-*/;

    public interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @Inject
    ApplicationView(Binder uiBinder) {
        Widget andBindUi = uiBinder.createAndBindUi(this);
        initWidget(andBindUi);
        modeselector.setDisplay(Display.NONE);
        bindSlot(ApplicationPresenter.SLOT_MainContent, main);
        bindSlot(ApplicationPresenter.SLOT_HeaderContent, header);
        addEventListener(this.asWidget().getElement());
        dialogWrapper.setVisible(false);
        register();
    }

    void register() {
        if (!registred) {
            registred = true;
            Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
                @Override
                public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
                    NativeEvent ne = event.getNativeEvent();
                    if ("keypress".equals(ne.getType()) && ne.getKeyCode() != 0) {
                        logger.log(Level.INFO, String.valueOf((char) ne.getKeyCode()));
                        ServerEventBusService.App.getInstance().onSendKeyEvent(ne.getKeyCode(), counter++, new VoidAsyncCallback());
                    } else if ("mousedown".equals(ne.getType()) && ne.getButton() == NativeEvent.BUTTON_RIGHT) {
                        ne.preventDefault();
                    }
                }
            });
        }
    }
}
