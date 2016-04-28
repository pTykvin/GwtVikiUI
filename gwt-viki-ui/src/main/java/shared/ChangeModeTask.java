package shared;

import de.novanic.eventservice.client.event.Event;

public class ChangeModeTask implements Event {

    private String mode;

    public ChangeModeTask(String mode) {
        this.mode = mode;
    }

    public ChangeModeTask() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
