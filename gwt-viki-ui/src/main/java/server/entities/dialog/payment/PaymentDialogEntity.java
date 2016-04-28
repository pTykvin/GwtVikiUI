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


import gwt.material.design.viki.client.share.dialog.payment.PaymentDialogInfo;
import gwt.material.design.viki.client.share.dialog.payment.ShowPaymentDialogTask;
import server.entities.Entity;
import server.entities.dialog.KeyboardAccumulator;
import server.entities.dialog.simple.DialogServerAction;

public class PaymentDialogEntity extends Entity<ShowPaymentDialogTask, PaymentDialogShadow, PaymentDialogInfo> {

    private DialogServerAction cashPayment;
    private DialogServerAction bankPayment;
    private DialogServerAction closeAction;

    private final KeyboardAccumulator accumulator;

    public PaymentDialogEntity(PaymentDialogInfo info, KeyboardAccumulator accumulator) {
        super(info);
        this.accumulator = accumulator;
    }

    @Override
    protected ShowPaymentDialogTask generateEvent(PaymentDialogShadow shadow) {
        ShowPaymentDialogTask task = new ShowPaymentDialogTask();
        task.setCashPayAction(shadow.registerEvent(cashPayment));
        task.setBankPayAction(shadow.registerEvent(bankPayment));
        task.setCloseAction(shadow.registerEvent(closeAction));
        return task;
    }

    @Override
    protected PaymentDialogShadow generateShadow(int entityId) {
        return new PaymentDialogShadow(accumulator, entityId);
    }

    public void setCashPayment(DialogServerAction cashPayment) {
        this.cashPayment = cashPayment;
    }

    public void setBankPayment(DialogServerAction bankPayment) {
        this.bankPayment = bankPayment;
    }

    public void setCloseAction(DialogServerAction closeAction) {
        this.closeAction = closeAction;
    }
}
