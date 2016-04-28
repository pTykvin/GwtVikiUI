package server.entities.dialog.simple;

import gwt.material.design.viki.client.share.dialog.simple.ShowSimpleDialogTask;
import gwt.material.design.viki.client.share.dialog.simple.SimpleDialogInfo;
import server.entities.Entity;

public class SimpleDialogEntity extends Entity<ShowSimpleDialogTask, SimpleDialogShadow, SimpleDialogInfo> {

    private DialogServerAction leftAction;
    private DialogServerAction rightAction;
    private DialogServerAction closeAction;

    public SimpleDialogEntity(SimpleDialogInfo info) {
        super(info);
    }

    @Override
    protected ShowSimpleDialogTask generateEvent(SimpleDialogShadow shadow) {
        ShowSimpleDialogTask event = new ShowSimpleDialogTask();
        event.setLeftAction(shadow.registerEvent(leftAction));
        event.setRightAction(shadow.registerEvent(rightAction));
        event.setCloseAction(shadow.registerEvent(closeAction));
        return event;
    }

    @Override
    protected SimpleDialogShadow generateShadow(int entityId) {
        return new SimpleDialogShadow(entityId);
    }


    public void setLeftAction(DialogServerAction leftAction) {
        this.leftAction = leftAction;
    }

    public void setRightAction(DialogServerAction rightAction) {
        this.rightAction = rightAction;
    }

    public void setCloseAction(DialogServerAction closeAction) {
        this.closeAction = closeAction;
    }

}
