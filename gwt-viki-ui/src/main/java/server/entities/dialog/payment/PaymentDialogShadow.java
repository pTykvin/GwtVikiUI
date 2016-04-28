package server.entities.dialog.payment;

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
