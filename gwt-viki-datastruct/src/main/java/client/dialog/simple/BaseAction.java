package client.dialog.simple;

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


import client.dialog.base.Action;
import client.dialog.base.VikiBooleanClientProperty;

public abstract class BaseAction<T> implements Action {

    private int actionId;

    private T info;
    private boolean closeable;
    private VikiBooleanClientProperty enabledProperty;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public VikiBooleanClientProperty getEnabledProperty() {
        return enabledProperty;
    }

    public void setEnabledProperty(VikiBooleanClientProperty enabledProperty) {
        this.enabledProperty = enabledProperty;
    }
}
