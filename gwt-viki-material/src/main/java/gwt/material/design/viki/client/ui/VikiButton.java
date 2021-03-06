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


import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import client.dialog.simple.DialogAction;
import client.dialog.simple.DialogActionInfo;

public class VikiButton extends MaterialButton {

    public static VikiButton build(DialogAction action) {
        return (action == null) ? null : new VikiButton(action);
    }

    private VikiButton(DialogAction action) {
        DialogActionInfo info = action.getInfo();
        setText(info.getText());
        setWaves(WavesType.valueOf(info.getButtonSkin().getWavesType()));
        setTextColor(info.getButtonSkin().getTextColor());
        setBackgroundColor(info.getButtonSkin().getBackgroundColor());
        addStyleName("viki-dialog-button");
        setShadow(2);
    }

}
