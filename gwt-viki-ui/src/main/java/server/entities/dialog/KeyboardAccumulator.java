package server.entities.dialog;

import java.util.function.Consumer;

import com.google.gwt.event.dom.client.KeyCodes;

public class KeyboardAccumulator {
    private String string = "";
    private final Consumer<String> listener;
    private boolean fastPar;

    public KeyboardAccumulator(Consumer<String> listener) {
        this.listener = listener;
    }

    public void applyKey(int keyCode) {
        if (keyCode == KeyCodes.KEY_BACKSPACE) {
            if (fastPar) {
                fastPar = false;
                string = "";
            } else if (!string.isEmpty()) {
                string = string.substring(0, string.length() - 1);
            }
        } else {
            if (fastPar) {
                fastPar = false;
                string = "";
            }
            string += Character.toString(((char) keyCode));
        }
        listener.accept(string);
    }

    public void applyString(String key) {
        fastPar = true;
        string = key;
        listener.accept(string);
    }
}
