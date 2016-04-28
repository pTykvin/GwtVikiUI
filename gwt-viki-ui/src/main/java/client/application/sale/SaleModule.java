package client.application.sale;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SaleModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SalePresenter.class, SalePresenter.MyView.class, SaleView.class, SalePresenter.MyProxy.class);
    }
}
