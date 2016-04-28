package server.entities.dialog.payment;

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
