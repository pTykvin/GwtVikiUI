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


import gwt.material.design.viki.client.share.dialog.simple.ShowSimpleDialogTask;
import gwt.material.design.viki.client.share.dialog.simple.SimpleDialogInfo;
import server.entities.Entity;

public class SimpleDialogEntity extends Entity<ShowSimpleDialogTask, SimpleDialogShadow, SimpleDialogInfo> {

    private DialogServerAction leftAction;
    private DialogServerAction rightAction;
    private DialogServerAction closeAction;

    public SimpleDialogEntity(SimpleDialogInfo info) {
        super(info);
    }

    @Override
    protected ShowSimpleDialogTask generateEvent(SimpleDialogShadow shadow) {
        ShowSimpleDialogTask event = new ShowSimpleDialogTask();
        event.setLeftAction(shadow.registerEvent(leftAction));
        event.setRightAction(shadow.registerEvent(rightAction));
        event.setCloseAction(shadow.registerEvent(closeAction));
        return event;
    }

    @Override
    protected SimpleDialogShadow generateShadow(int entityId) {
        return new SimpleDialogShadow(entityId);
    }


    public void setLeftAction(DialogServerAction leftAction) {
        this.leftAction = leftAction;
    }

    public void setRightAction(DialogServerAction rightAction) {
        this.rightAction = rightAction;
    }

    public void setCloseAction(DialogServerAction closeAction) {
        this.closeAction = closeAction;
    }

}
