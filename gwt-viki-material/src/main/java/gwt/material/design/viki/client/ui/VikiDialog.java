package gwt.material.design.viki.client.ui;

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

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.html.Div;
import client.dialog.base.Info;
import client.dialog.base.Task;
import client.dialog.simple.DialogAction;
import gwt.material.design.viki.client.utils.JsUtils;

public abstract class VikiDialog<T extends Task<I>, I extends Info> extends Div {

    protected final T task;
    protected final int entityId;

    private MaterialLink close;
    private HandlerRegistration closeHandler;

    public VikiDialog(T task) {
        this.task = task;
        this.entityId = task.getEntityId();
        baseConstruct();
        baseStylize();
        registerCloseAction(task.getCloseAction());
        registerProperties(task);
    }

    protected abstract void stylize();
    protected abstract void construct();
    protected abstract void registerProperties(T task);
    protected abstract void unregisterProperties(T task);

    private void baseStylize() {
        close.addStyleName("viki-dialog-close");
        close.setIconType(IconType.CLEAR);
        close.setIconSize(IconSize.SMALL);
        close.setTextAlign(TextAlign.CENTER);
        close.setFloat(Style.Float.LEFT);
        close.setIconPosition(IconPosition.NONE);
        stylize();
    }

    private void baseConstruct() {
        close = new MaterialLink();
        add(close);
        construct();
    }

    public final void closeModal() {
        unregisterProperties(task);
        JsUtils.closeVikiDialog();
    }

    public final void registerCloseAction(DialogAction closeAction) {
        if (closeAction != null) {
            releaseHandler(closeHandler);
            closeHandler = addHandler(close, closeAction);
        }
    }

    protected HandlerRegistration addHandler(final HasClickHandlers handler, final DialogAction action) {
        if (handler == null || action == null) {
            return null;
        } else {
            return handler.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    JsUtils.customDialogEvent(entityId, action.getActionId());
                    if (action.isCloseable()) {
                        closeModal();
                    }
                }
            });
        }
    }

    protected final void releaseHandler(HandlerRegistration handler) {
        if (handler != null) {
            handler.removeHandler();
        }
    }

}
