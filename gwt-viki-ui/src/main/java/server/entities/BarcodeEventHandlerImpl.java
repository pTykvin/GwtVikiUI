package server.entities;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class BarcodeEventHandlerImpl implements BarcodeEventHandler {

    private Consumer<String> barcodeListener;
    private final long delay;
    private StringBuilder barcode = new StringBuilder();
    private AtomicLong stopTimestamp = new AtomicLong();
    private boolean started;

    public BarcodeEventHandlerImpl(long delay) {
        this.delay = delay;
    }

    @Override
    public void keyboardEvent(String s) {
        barcode.append(s);
        startBarcode();
    }

    private synchronized void startBarcode() {
        stopTimestamp.set(System.currentTimeMillis() + delay);
        if (!started) {
            started = true;
            Executors.newCachedThreadPool().execute(() -> {
                while (System.currentTimeMillis() < stopTimestamp.get()) {
                    Thread.yield();
                }
                barcodeListener.accept(barcode.toString());
                barcode = new StringBuilder();
                started = false;
            });
        }

    }

    @Override
    public void onBarcodeHandling(Consumer<String> barcodeListener) {
        this.barcodeListener = barcodeListener;
    }

}
