package client.application.sale;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import client.application.ApplicationPresenter;
import client.event.SetPageTitleEvent;
import client.event.TabEvent;
import client.place.NameTokens;
import client.rpc.ServerEventBusService;
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import shared.AddPositionToPurchaseEvent;
import shared.ChangePurchasePositionEvent;
import shared.ClearPurchaseTask;
import shared.PurchasePositionEvent;

public class SalePresenter extends Presenter<SalePresenter.MyView, SalePresenter.MyProxy> {
    public interface MyView extends View {
        void addPositionToPurchase(AddPositionToPurchaseEvent anEvent);

        void changePosition(ChangePurchasePositionEvent anEvent);

        void updateSum(Long purchaseSum);

        void setEmpty(boolean empty);

        void clearPurchase();
    }

    @ProxyStandard
    @NameToken(NameTokens.sale)
    public interface MyProxy extends ProxyPlace<SalePresenter> {
    }

    @Inject
    SalePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MainContent);
        RemoteEventService remoteEventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
        remoteEventService.addListener(ServerEventBusService.MODE_SELECTOR_DOMAIN, new RemoteEventListener() {
            @Override
            public void apply(Event anEvent) {
                if (anEvent instanceof PurchasePositionEvent) {
                    if (anEvent instanceof AddPositionToPurchaseEvent) {
                        getView().addPositionToPurchase(((AddPositionToPurchaseEvent) anEvent));
                    } else if (anEvent instanceof ChangePurchasePositionEvent) {
                        getView().changePosition(((ChangePurchasePositionEvent) anEvent));
                    }
                    getView().updateSum(((PurchasePositionEvent) anEvent).getPurchaseSum());
                } else if (anEvent instanceof ClearPurchaseTask) {
                    getView().clearPurchase();
                }
            }
        });

        eventBus.addHandler(TabEvent.TYPE, new TabEvent.Handler() {
            @Override
            public void onTabChange(TabEvent event) {
                boolean empty = !("#1".equals(event.getTabId()));
                getView().setEmpty(empty);
            }
        });
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        SetPageTitleEvent.fire("GWT Material", "A Material Design look and feel for GWT Apps plus Phonegap.", this);
    }
}