package server;

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


import client.ButtonSkin;
import client.DoubtButtonSkin;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.WavesType;

public class UISkins {

    // Common skins
    public static final ButtonSkin BUTTON_WHITE = new ButtonSkin();
    public static final ButtonSkin BUTTON_BLUE = new ButtonSkin();
    public static final ButtonSkin BUTTON_RED = new ButtonSkin();
    public static final ButtonSkin BUTTON_GREY = new ButtonSkin();

    // Specific skins
    public static final ButtonSkin BACK_PAYMENT_BUTTON = new ButtonSkin();
    public static final DoubtButtonSkin CLEAR_PURCHASE_BUTTON = new DoubtButtonSkin();

    static {

        BUTTON_WHITE.setTextColor("black");
        BUTTON_WHITE.setBackgroundColor("white");
        BUTTON_WHITE.setWavesType(WavesType.DEFAULT.name());
        BUTTON_WHITE.setType(ButtonType.RAISED);

        BUTTON_BLUE.setTextColor("white");
        BUTTON_BLUE.setBackgroundColor("blue");
        BUTTON_BLUE.setWavesType(WavesType.LIGHT.name());
        BUTTON_BLUE.setType(ButtonType.RAISED);

        BUTTON_RED.setTextColor("white");
        BUTTON_RED.setBackgroundColor("red darken-1");
        BUTTON_RED.setWavesType(WavesType.LIGHT.name());
        BUTTON_RED.setType(ButtonType.RAISED);

        BUTTON_GREY.setTextColor("grey lighten-2");
        BUTTON_GREY.setBackgroundColor("transparent");
        BUTTON_GREY.setWavesType(null);
        BUTTON_GREY.setType(ButtonType.FLAT);


        BACK_PAYMENT_BUTTON.setWavesType(WavesType.DEFAULT.name());
        BACK_PAYMENT_BUTTON.setIconName("bank_card");

        CLEAR_PURCHASE_BUTTON.setDoubtButtonSkin(BUTTON_RED);
        CLEAR_PURCHASE_BUTTON.setNoDoubtButtonSkin(BUTTON_GREY);

    }
}
