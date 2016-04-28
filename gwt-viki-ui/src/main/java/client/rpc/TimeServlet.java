package client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import shared.MyObject;

@RemoteServiceRelativePath("TimeServlet")
public interface TimeServlet extends RemoteService {
    String getTime();

    String getDate();

    MyObject getObject();

    class App {
        private static TimeServletAsync ourInstance = GWT.create(TimeServlet.class);

        public static synchronized TimeServletAsync getInstance() {
            return ourInstance;
        }
    }
}
