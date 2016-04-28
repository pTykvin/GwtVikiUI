package server.entities;

import java.util.function.Consumer;

public interface BarcodeEventHandler {
    void keyboardEvent(String s);
    void onBarcodeHandling(Consumer<String> barcodeListener);
}
