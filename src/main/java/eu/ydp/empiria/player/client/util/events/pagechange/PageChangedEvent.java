package eu.ydp.empiria.player.client.util.events.pagechange;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class PageChangedEvent extends Event {
	private final JSONObject payload;

	public PageChangedEvent(int newPage) {
		super("page_changed");
		this.payload = new JSONObject();
		this.payload.put("new_page", new JSONNumber(newPage));
	}

	@Override
	public JSONObject getPayload() {
		return payload;
	}
}
