package server.entities;

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


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import client.dialog.base.Info;
import client.dialog.base.Task;

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
