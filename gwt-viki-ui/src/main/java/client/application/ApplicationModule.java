package client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import client.application.header.HeaderModule;
import client.application.loader.LoaderModule;
import client.application.sale.SaleModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class,
                ApplicationView.class, ApplicationPresenter.MyProxy.class);

        install(new LoaderModule());
        install(new HeaderModule());
        install(new SaleModule());

    }
}
