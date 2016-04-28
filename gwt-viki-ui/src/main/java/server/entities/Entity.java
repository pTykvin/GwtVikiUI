package server.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import gwt.material.design.viki.client.share.dialog.base.Info;
import gwt.material.design.viki.client.share.dialog.base.Task;

public abstract class Entity<E extends Task<I>, S extends Shadow, I extends Info> {

    private static Map<Integer, Entity> entities = new HashMap<>();
    private static AtomicInteger idGenerator = new AtomicInteger();
    private int key;
    private E event;
    private S shadow;
    private I info;

    public Entity(I info) {
        int key = idGenerator.incrementAndGet();
        this.key = key;
        this.info = info;
        entities.put(key, this);
    }

    protected abstract E generateEvent(S shadow);
    protected abstract S generateShadow(int key);

    protected final void remove() {
        entities.remove(key);
    }

    public E getEvent() {
        if (event == null) {
            event = generateEvent(getShadow());
            event.setInfo(info);
            event.setEntityId(key);
        }
        return event;
    }

    public S getShadow() {
        if (shadow == null) {
            shadow = generateShadow(key);
        }
        return shadow;
    }

    public void doSuicide() {
        entities.remove(key);
    }

    public static void handleAction(int componentId, int eventId) {
        Entity entity = entities.get(componentId);
        Shadow shadow = entity.getShadow();
        shadow.apply(eventId);
        if (shadow.isEntityKiller(eventId)) {
            entities.remove(componentId);
        }
    }

    public static void onSendStringEvent(int entityId, String key) {
        Entity entity = entities.get(entityId);
        Shadow shadow = entity.getShadow();
        if (shadow instanceof KeyboardListener) {
            ((KeyboardListener) shadow).applyString(key);
        }
    }

    public static void onSendKeyEvent(int entityId, int key) {
        Entity entity = entities.get(entityId);
        Shadow shadow = entity.getShadow();
        if (shadow instanceof KeyboardListener) {
            ((KeyboardListener) shadow).applyKey(key);
        }
    }
}
