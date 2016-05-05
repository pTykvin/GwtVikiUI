package server.entities;

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
