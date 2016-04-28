package server;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.viki.client.share.ButtonSkin;
import gwt.material.design.viki.client.share.DoubtButtonSkin;

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
