package server.entities.dialog.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import gwt.material.design.viki.client.share.dialog.simple.DialogAction;
import server.entities.Shadow;

public class SimpleDialogShadow extends Shadow<DialogAction, DialogServerAction> {
    private final Map<Integer, DialogServerAction> actions = new HashMap<>();
    private final AtomicInteger idActionGenerator = new AtomicInteger();

    public SimpleDialogShadow(int entityId) {
        super(entityId);
    }

    @Override
    public void apply(int eventId) {
        Optional.ofNullable(actions.get(eventId).getRunnable()).ifPresent(Runnable::run);
    }

    @Override
    public boolean isEntityKiller(int eventId) {
        return actions.get(eventId).isEntityKiller();
    }

    @Override
    public DialogAction registerEvent(final DialogServerAction serverEvent) {
        if (serverEvent == null) {
            return null;
        }
        int idEvent = idActionGenerator.incrementAndGet();
        actions.put(idEvent, serverEvent);
        DialogAction clientAction = new DialogAction();
        clientAction.setActionId(idEvent);
        clientAction.setInfo(serverEvent.getInfo());
        clientAction.setCloseable(serverEvent.isEntityKiller());
        clientAction.setEnabledProperty(createClientBooleanProperty(serverEvent.enabledProperty()));
        return clientAction;
    }

}
