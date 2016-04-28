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


import javax.inject.Inject;

import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.presenter.slots.NestedSlot;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.Proxy;

import client.VoidAsyncCallback;
import client.application.header.HeaderPresenter;
import client.application.places.ModePlace;
import client.application.sale.SalePresenter;
import client.event.SetPageTitleEvent;
import client.rpc.ServerEventBusService;
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import gwt.material.design.viki.client.share.dialog.base.VikiBooleanClientProperty;
import gwt.material.design.viki.client.share.dialog.payment.ShowPaymentDialogTask;
import gwt.material.design.viki.client.share.dialog.simple.ShowSimpleDialogTask;
import gwt.material.design.viki.client.utils.JsUtils;
import gwt.material.design.viki.client.utils.PropertyRegistrator;
import shared.ChangeModeTask;
import shared.CloseDialogTask;
import shared.ModifyFocusedContentTask;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
        implements SetPageTitleEvent.SetPageTitleHandler, NavigationHandler {
    private final HeaderPresenter headerPresenter;
    private final SalePresenter salePresenter;

    public interface MyView extends View {
        void setPageTitle(String title, String description);

        void setMode(String mode);

        void showSimpleDialog(ShowSimpleDialogTask anEvent);

        void showPaymentDialog(ShowPaymentDialogTask anEvent);

        void closeDialog();
    }

    public static final NestedSlot SLOT_MainContent = new NestedSlot();
    public static final NestedSlot SLOT_HeaderContent = new NestedSlot();


    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    @Inject
    ApplicationPresenter(EventBus eventBus, MyView view, HeaderPresenter headerPresenter, SalePresenter salePresenter, MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);
        this.headerPresenter = headerPresenter;
        this.salePresenter = salePresenter;
        RemoteEventService remoteEventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
        remoteEventService.addListener(ServerEventBusService.MODE_SELECTOR_DOMAIN, new RemoteEventListener() {
            @Override
            public void apply(Event anEvent) {
                if (anEvent instanceof ChangeModeTask) {
                    String token = ((ChangeModeTask) anEvent).getMode();
                    getView().setMode(token);
                    getEventBus().fireEvent(new PlaceChangeEvent(new ModePlace(token)));
                }
                else if (anEvent instanceof ShowSimpleDialogTask) {
                    getView().showSimpleDialog(((ShowSimpleDialogTask) anEvent));
                }
                else if (anEvent instanceof ShowPaymentDialogTask) {
                    getView().showPaymentDialog(((ShowPaymentDialogTask) anEvent));
                }
                else if (anEvent instanceof CloseDialogTask) {
                    getView().closeDialog();
                }
                else if (anEvent instanceof ModifyFocusedContentTask) {
                    JsUtils.fireString(((ModifyFocusedContentTask) anEvent).getContent());
                }
                else if (anEvent instanceof VikiBooleanClientProperty) {
                    VikiBooleanClientProperty property = (VikiBooleanClientProperty) anEvent;
                    PropertyRegistrator.fireBooleanEvent(property);
                }

            }
        });

        ServerEventBusService.App.getInstance().start(new VoidAsyncCallback());
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(SetPageTitleEvent.TYPE, this);
        addRegisteredHandler(NavigationEvent.getType(), this);

        getView().setInSlot(SLOT_HeaderContent, headerPresenter);
        getView().setInSlot(SLOT_MainContent, salePresenter);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
    }

    @Override
    public void onSetPageTitle(SetPageTitleEvent event) {
        getView().setPageTitle(event.getTitle(), event.getDescription());
    }

    @Override
    public void onNavigation(NavigationEvent navigationEvent) {
        Window.scrollTo(0, 0);
    }
}
