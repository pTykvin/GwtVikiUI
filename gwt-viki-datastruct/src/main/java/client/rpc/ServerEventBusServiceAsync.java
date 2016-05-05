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

import com.google.gwt.user.client.rpc.AsyncCallback;

import shared.Product;

public interface ServerEventBusServiceAsync {
    void start(AsyncCallback<Void> callback);

    void onTileClickEvent(String item, AsyncCallback<Void> async);

    void onDialogEvent(int entityId, int eventId, AsyncCallback<Void> async);

    void getTiles(AsyncCallback<Map<Integer, Product>> async);

    void clearPurchase(AsyncCallback<Void> async);

    void dialogException(String message, AsyncCallback<Void> async);

    void onSendStringEvent(int entityId, String key, AsyncCallback<Void> async);

    void onSendTouchKeyEvent(int entityId, int key, AsyncCallback<Void> async);

    void onSubTotal(AsyncCallback<Void> async);

    void onSendKeyEvent(int keyCode, long number, AsyncCallback<Void> async);
}
