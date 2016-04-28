package client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class TabEvent extends GwtEvent<TabEvent.Handler> {

    public interface Handler extends EventHandler {
        void onTabChange(TabEvent event);
    }

    public static final Type<Handler> TYPE = new Type<>();

    private final String tabId;

    public TabEvent(String tabId) {
        this.tabId = tabId;
    }

    public static void fire(String tabId, HasHandlers source) {
        source.fireEvent(new TabEvent(tabId));
    }

    public String getTabId() {
        return tabId;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onTabChange(this);
    }
}