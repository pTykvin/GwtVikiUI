package server.entities.dialog.payment;

import server.entities.KeyboardListener;
import server.entities.dialog.KeyboardAccumulator;
import server.entities.dialog.simple.SimpleDialogShadow;

public class PaymentDialogShadow extends SimpleDialogShadow implements KeyboardListener {

    private final KeyboardAccumulator accumulator;

    public PaymentDialogShadow(KeyboardAccumulator accumulator, int entityId) {
        super(entityId);
        this.accumulator = accumulator;
    }

    @Override
    public void applyKey(int keyCode) {
        accumulator.applyKey(keyCode);
    }

    @Override
    public void applyString(String key) {
        accumulator.applyString(key);
    }
}
