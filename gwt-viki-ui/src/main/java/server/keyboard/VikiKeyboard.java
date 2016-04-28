package server.keyboard;

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
