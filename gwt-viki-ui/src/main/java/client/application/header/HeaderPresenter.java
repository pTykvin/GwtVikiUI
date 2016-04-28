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
