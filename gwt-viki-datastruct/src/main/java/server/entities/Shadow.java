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


import java.util.concurrent.atomic.AtomicInteger;

import client.dialog.base.Action;
import client.dialog.base.VikiBooleanClientProperty;

public abstract class Shadow<C extends Action, S extends ServerAction> {

    private final int entityId;

    private final AtomicInteger idPropertyGenerator = new AtomicInteger();

    public Shadow(int entityId) {
        this.entityId = entityId;
    }

    public abstract void apply(int eventId);

    public abstract boolean isEntityKiller(int eventId);

    public abstract C registerEvent(S serverEvent);


    protected VikiBooleanClientProperty createClientBooleanProperty(VikiBooleanProperty vikiBooleanProperty) {
        VikiBooleanClientProperty property = new VikiBooleanClientProperty();
        int propertyId = idPropertyGenerator.incrementAndGet();
        property.setPropertyId(propertyId);
        property.setEntityId(entityId);
        property.setValue(vikiBooleanProperty.get());
        PropertyHandler.registerBooleanProperty(vikiBooleanProperty, property);
        return property;
    }
}
