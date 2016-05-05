package client.rpc;

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


import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import shared.Product;

@RemoteServiceRelativePath("ServerEventBusService")
public interface ServerEventBusService extends RemoteService {

    Domain MODE_SELECTOR_DOMAIN = DomainFactory.getDomain("mode_selector_domain");

    /**
     * Utility class for simplifying access to the instance of async service.
     */

    void start();

    void onTileClickEvent(String item);

    void onSubTotal();

    void clearPurchase();

    Map<Integer,Product> getTiles();

    void onDialogEvent(int entityId, int eventId);

    void dialogException(String message);

    void onSendStringEvent(int entityId, String key);

    void onSendTouchKeyEvent(int entityId, int key);

    void onSendKeyEvent(int keyCode, long number);

    class App {
        private static ServerEventBusServiceAsync ourInstance = GWT.create(ServerEventBusService.class);

        public static synchronized ServerEventBusServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
