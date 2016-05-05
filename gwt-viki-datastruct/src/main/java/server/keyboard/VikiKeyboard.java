package server.keyboard;

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


import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class VikiKeyboard {
    private static final long KEYBOARD_TIMEOUT = 30L;
    private BiConsumer<String, String> onMSR;
    private Consumer<String> onBarcode;
    private TypedCharactersQueue typedCharactersQueue;
    private ExecutorService keyHandler = Executors.newSingleThreadExecutor();

    public void onBarcode(Consumer<String> onBarcode) {
        this.onBarcode = onBarcode;
    }

    public void onMSR(BiConsumer<String, String> onMSR) {
        this.onMSR = onMSR;
    }

    public void start() {
        typedCharactersQueue = new TypedCharactersQueue(KEYBOARD_TIMEOUT);
        keyHandler.execute(new VikiKeyHandlerThread(this, typedCharactersQueue));
    }

    public void processMSR(String track1, String track2) {
        if (onMSR != null) {
            onMSR.accept(track1, track2);
        }
    }

    public void processScanner(Queue<Character> characters) {
        if (onBarcode == null) {
            return;
        }
        if (characters.isEmpty()) {
            return;
        }
        String barcode = StringUtils.trimToNull(characters.stream()
            .map(String::valueOf)
            .collect(Collectors.joining()));
        characters.clear();

        if (barcode == null) {
            return;
        }
        onBarcode.accept(barcode);
    }

    public void onRawKeyEvent(server.keyboard.KeyCode keyCode) {
        typedCharactersQueue.add(keyCode);
    }

}
