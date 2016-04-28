package client.rpc;

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