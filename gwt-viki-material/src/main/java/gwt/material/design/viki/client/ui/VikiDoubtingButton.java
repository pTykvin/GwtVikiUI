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


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import gwt.material.design.client.ui.MaterialButton;
import client.dialog.simple.DoubtAction;
import gwt.material.design.viki.client.utils.JsUtils;

public class VikiDoubtingButton extends MaterialButton {
    private final int entityId;
    private boolean doubtingNow;
    private DoubtAction action;

    public VikiDoubtingButton(int entityId) {

        this.entityId = entityId;

        super.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (doubtingNow) {
                    doAction();
                } else {
                    startDoubt();
                }
            }
        });

        super.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                if (doubtingNow) {
                    stopDoubt();
                }
            }
        });
    }

    private void startDoubt() {
        doubtingNow = true;
    }

    private void stopDoubt() {
        doubtingNow = false;
    }

    private void doAction() {
        JsUtils.customDialogEvent(entityId, action.getActionId());
    }

    public void setAction(DoubtAction action) {
        this.action = action;
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        throw new IllegalArgumentException("You can't handle click exception. Use setAction(DialogAction) method");
    }
}
