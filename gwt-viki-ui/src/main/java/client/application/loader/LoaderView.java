package client.application.loader;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

import gwt.material.design.client.ui.MaterialProgress;

public class LoaderView extends ViewImpl implements LoaderPresenter.MyView {

    @UiField
    MaterialProgress progress;

    @UiField
    HTMLPanel loader;

    private Timer timer;
    private double p = 0;

    @Inject
    LoaderView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        timer = new Timer() {
            @Override
            public void run() {
                if ((p += 20) >= 100) {
                    timer.cancel();
                }
                progress.setPercent(p);
            }
        };
        timer.scheduleRepeating(250);
    }

    public interface Binder extends UiBinder<Widget, LoaderView> {
    }
}
