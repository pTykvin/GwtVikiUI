package client.dialog.payment;

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


import client.dialog.base.Task;
import client.dialog.simple.DialogAction;

public class ShowPaymentDialogTask extends Task<PaymentDialogInfo> {

    private DialogAction cashPayAction;
    private DialogAction bankPayAction;
    private DialogAction closeAction;

    public DialogAction getCashPayAction() {
        return cashPayAction;
    }

    public void setCashPayAction(DialogAction cashPayAction) {
        this.cashPayAction = cashPayAction;
    }

    public DialogAction getBankPayAction() {
        return bankPayAction;
    }

    public void setBankPayAction(DialogAction bankPayAction) {
        this.bankPayAction = bankPayAction;
    }

    public DialogAction getCloseAction() {
        return closeAction;
    }

    public void setCloseAction(DialogAction closeAction) {
        this.closeAction = closeAction;
    }
}
