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


import client.dialog.base.Info;

public class PaymentDialogInfo implements Info {

    private String purchaseSum;
    private int[] pars = {50, 100, 500, 1000, 5000};
    private boolean[] parsState = new boolean[5];

    public String getPurchaseSum() {
        return purchaseSum;
    }

    public void setPurchaseSum(String purchaseSum) {
        this.purchaseSum = purchaseSum;
    }

    public int[] getPars() {
        return pars;
    }

    public void setPars(int... pars) {
        this.pars = pars;
    }

    public boolean[] getParsState() {
        return parsState;
    }

    public void setParsState(boolean... parsState) {
        this.parsState = parsState;
    }
}
