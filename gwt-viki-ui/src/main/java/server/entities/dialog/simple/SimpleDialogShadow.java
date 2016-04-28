package server.entities.dialog.simple;

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
