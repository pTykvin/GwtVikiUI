package client.application.loader;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import client.application.ApplicationPresenter;
import client.event.SetPageTitleEvent;
import client.place.NameTokens;

public class LoaderPresenter extends Presenter<LoaderPresenter.MyView, LoaderPresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken(NameTokens.loader)
    public interface MyProxy extends ProxyPlace<LoaderPresenter> {
    }

    @Inject
    LoaderPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MainContent);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        SetPageTitleEvent.fire("GWT Material", "A Material Design look and feel for GWT Apps plus Phonegap.", this);
    }
}
