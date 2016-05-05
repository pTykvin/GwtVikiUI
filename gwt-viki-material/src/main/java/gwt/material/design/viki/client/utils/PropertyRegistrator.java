package gwt.material.design.viki.client.utils;

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

import com.google.gwt.user.client.Window;

import client.dialog.base.VikiBooleanClientProperty;
import client.dialog.base.VikiBooleanListener;

public class PropertyRegistrator {
    private static Map<Integer, Map<Integer, VikiBooleanListener>> struct = new HashMap<>();

    public static void registerBooleanProperty(VikiBooleanClientProperty booleanProperty, VikiBooleanListener vikiBooleanListener) {
        if (booleanProperty != null) {
            int entityId = booleanProperty.getEntityId();
            boolean value = booleanProperty.getValue();
            int propertyId = booleanProperty.getPropertyId();

            Map<Integer, VikiBooleanListener> listener;
            if (struct.containsKey(entityId)) {
                listener = struct.get(entityId);
            } else {
                listener = new HashMap<>();
            }

            vikiBooleanListener.onChangeValue(value);
            listener.put(propertyId, vikiBooleanListener);
            struct.put(entityId, listener);
        }
    }

    public static void fireBooleanEvent(VikiBooleanClientProperty booleanProperty) {
        int entityId = booleanProperty.getEntityId();
        if (struct.containsKey(entityId)) {
            int propertyId = booleanProperty.getPropertyId();
            Map<Integer, VikiBooleanListener> listenerMap = struct.get(entityId);
            if (listenerMap.containsKey(propertyId)) {
                listenerMap.get(propertyId).onChangeValue(booleanProperty.getValue());
            } else {
                Window.alert(PropertyRegistrator.class.getName()+"Property " + propertyId + " not registred!");
            }
        } else {
            Window.alert(PropertyRegistrator.class.getName()+"Entity " + entityId + " not registred!");
        }
    }

    public static void dispose(VikiBooleanClientProperty booleanProperty) {
        struct.remove(booleanProperty.getEntityId());
    }
}
