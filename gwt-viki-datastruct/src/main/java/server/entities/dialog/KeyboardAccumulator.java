package server.entities.dialog;

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
