package server.entities.dialog.simple;

import gwt.material.design.viki.client.share.dialog.simple.DialogActionInfo;
import server.entities.ServerAction;
import server.entities.VikiBooleanProperty;

public class DialogServerAction implements ServerAction {

    private final Runnable runnable;
    private final DialogActionInfo info;
    private final boolean entityKiller;

    private final VikiBooleanProperty enabledProperty = VikiBooleanProperty.of(true);

    public DialogServerAction(Runnable runnable, DialogActionInfo info, boolean entityKiller) {
        this.runnable = runnable;
        this.info = info;
        this.entityKiller = entityKiller;
    }

    public DialogServerAction(Runnable runnable, DialogActionInfo info) {
        this(runnable, info, false);
    }


    public Runnable getRunnable() {
        return runnable;
    }

    public DialogActionInfo getInfo() {
        return info;
    }

    public boolean isEntityKiller() {
        return entityKiller;
    }

    public void setEnabled(boolean enable) {
        enabledProperty.setInner(enable);
    }

    public VikiBooleanProperty enabledProperty() {
        return enabledProperty;
    }

    public boolean getEnabled() {
        return enabledProperty.get();
    }
}
