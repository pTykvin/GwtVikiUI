package client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import shared.MyObject;

public interface TimeServletAsync {
    void getTime(AsyncCallback<String> async);
    void getDate(AsyncCallback<String> async);
    void getObject(AsyncCallback<MyObject> async);
}
