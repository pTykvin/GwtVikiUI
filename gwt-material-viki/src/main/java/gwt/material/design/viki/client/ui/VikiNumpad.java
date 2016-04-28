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


import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.client.ui.html.Span;
import gwt.material.design.viki.client.utils.JsUtils;

public class VikiNumpad extends Div {
    private int entityId;

    {
        construct();
        stylize();
    }

    public VikiNumpad(Element destination, int entityId) {
        this.entityId = entityId;
        JsUtils.bindDestination(destination);
    }


    private void stylize() {
        setStyleName("viki-numpad");
    }

    private void construct() {
        String keys = "789456123,0";
        for (final char c : keys.toCharArray()) {
            Span buttonWrapper = new Span();
            buttonWrapper.setStyleName("key-wrapper");
            MaterialButton child = new MaterialButton(ButtonType.FLAT, String.valueOf(c), new MaterialIcon(IconType.BACKSPACE, "black", "white"));
            child.addStyleName("key");
            child.setWaves(WavesType.DEFAULT);
            child.addStyleName("center");
            child.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    JsUtils.sendKeyCode(entityId, (int)c);
                }
            });
            buttonWrapper.add(child);
            add(buttonWrapper);
        }
        Span buttonWrapper = new Span();
        buttonWrapper.setStyleName("key-wrapper");

        MaterialButton child =new MaterialButton();
        child.setIconType(IconType.BACKSPACE);
        child.setIconColor("grey darken-3");
        child.setIconFontSize(32, Style.Unit.PX);
        child.setIconSize(IconSize.SMALL);
        child.setTextAlign(TextAlign.CENTER);
        child.setIconPosition(IconPosition.NONE);
        child.setType(ButtonType.FLAT);
        child.setCircle(true);
        child.setWaves(WavesType.DEFAULT);
        child.addStyleName("key");

        child.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                JsUtils.sendKeyCode(entityId, KeyCodes.KEY_BACKSPACE);
            }
        });
        buttonWrapper.add(child);
        add(buttonWrapper);
    }

    public void dispose(){
        JsUtils.bindDestination(null);
    };
}
