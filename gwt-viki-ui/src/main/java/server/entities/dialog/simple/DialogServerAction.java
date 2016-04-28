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


import gwt.material.design.viki.client.share.dialog.simple.DialogActionInfo;
import server.entities.ServerAction;
import server.entities.VikiBooleanProperty;

public class DialogServerAction implements ServerAction {

    private final Runnable runnable;
    private final DialogActionInfo info;
    private final boolean entityKiller;

    private final VikiBooleanProperty enabledProperty = VikiBooleanProperty.of(true);

    public DialogServerAction(Runnable runnable, DialogActionInfo info, boolean entityKiller) {
        this.runnable = runnable;
        this.info = info;
        this.entityKiller = entityKiller;
    }

    public DialogServerAction(Runnable runnable, DialogActionInfo info) {
        this(runnable, info, false);
    }


    public Runnable getRunnable() {
        return runnable;
    }

    public DialogActionInfo getInfo() {
        return info;
    }

    public boolean isEntityKiller() {
        return entityKiller;
    }

    public void setEnabled(boolean enable) {
        enabledProperty.setInner(enable);
    }

    public VikiBooleanProperty enabledProperty() {
        return enabledProperty;
    }

    public boolean getEnabled() {
        return enabledProperty.get();
    }
}
