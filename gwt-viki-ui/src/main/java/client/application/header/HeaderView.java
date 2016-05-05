package client.application.header;

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


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewImpl;

import client.application.places.ModePlace;
import client.event.TabEvent;
import client.NameTokens;
import client.rpc.TimeServlet;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import shared.MyObject;

public class HeaderView extends ViewImpl implements HeaderPresenter.MyView {

    @UiField
    MaterialLabel time;

    @UiField
    MaterialPanel status;

    @UiField
    MaterialRow header;
    @UiField
    MaterialTab tabs;

    private EventBus eventBus;

    @Inject
    HeaderView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    protected void initWidget(IsWidget widget) {
        super.initWidget(widget);
        updateTime();
        new Timer() {
            @Override
            public void run() {
                updateTime();
            }
        }.scheduleRepeating(1000);
    }
    @Override
    public void placeChanged(Place newPlace) {
        if (newPlace instanceof ModePlace) {
            ModePlace.Tokenizer tokenizer = new ModePlace.Tokenizer();
            String token = tokenizer.getToken((ModePlace) newPlace);
            switch (token) {
                case NameTokens.sale:
                    status.setBackgroundColor("green darken-2");
                    header.setDisplay(Display.BLOCK);
                    tabs.setTabIndex(0);
                    break;
                case NameTokens.loader:
                    status.setBackgroundColor("grey darken-4");
                    header.setDisplay(Display.NONE);
                    break;
            }
        }
    }

    @Override
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public interface Binder extends UiBinder<Widget, HeaderView> {
    }

    private void updateTime() {
        TimeServlet.App.getInstance().getObject(new AsyncCallback<MyObject>() {
            @Override
            public void onFailure(Throwable caught) {
                time.setText(caught.getMessage());
            }

            @Override
            public void onSuccess(MyObject result) {
                time.setText(result.getTime());
            }
        });
    }

    @UiHandler(value = {"tab1", "tab2", "tab3", "tab4", "tab5"})
    public void onTabClicked(ClickEvent e) {
        MaterialLink link = (MaterialLink) e.getSource();
        eventBus.fireEvent(new TabEvent(link.getHref()));
    }

}
