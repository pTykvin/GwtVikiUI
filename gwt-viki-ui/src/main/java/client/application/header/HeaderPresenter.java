package client.application.header;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;

import client.application.ApplicationPresenter;
import client.event.SetPageTitleEvent;

public class HeaderPresenter extends Presenter<HeaderPresenter.MyView, HeaderPresenter.MyProxy> {

    public interface MyView extends View {
        void placeChanged(Place newPlace);

        void setEventBus(EventBus eventBus);
    }

    @ProxyStandard
    public interface MyProxy extends Proxy<HeaderPresenter> {
    }

    @Inject
    HeaderPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_HeaderContent);
        eventBus.addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
            @Override
            public void onPlaceChange(PlaceChangeEvent event) {
                getView().placeChanged(event.getNewPlace());
            }
        });
        getView().setEventBus(eventBus);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SetPageTitleEvent.fire("GWT Material", "A Material Design look and feel for GWT Apps plus Phonegap.", this);
    }
}
