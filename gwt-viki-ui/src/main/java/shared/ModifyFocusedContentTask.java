package shared;

import de.novanic.eventservice.client.event.Event;

public class ModifyFocusedContentTask implements Event {
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
