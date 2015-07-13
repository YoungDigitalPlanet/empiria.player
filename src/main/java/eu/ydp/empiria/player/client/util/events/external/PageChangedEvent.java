package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class PageChangedEvent extends ExternalEvent {

    public static final String EVENT_NAME = "page_changed";
    public static final String PAGE_INDEX_FIELD = "new_page";
    private final JSONObject payload;

    public PageChangedEvent(int newPage) {
        this.payload = new JSONObject();
        this.payload.put(PAGE_INDEX_FIELD, new JSONNumber(newPage));
    }

    @Override
    protected JSONObject getPayload() {
        return payload;
    }

    @Override
    protected String getEventType() {
        return EVENT_NAME;
    }
}
