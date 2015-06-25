package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class PageChangedEvent extends ExternalEvent {

	private final JSONObject payload;

	public PageChangedEvent(int newPage) {
		super("page_changed");
		this.payload = new JSONObject();
		this.payload.put("new_page", new JSONNumber(newPage));
	}

	@Override
	protected JSONObject getPayload() {
		return payload;
	}
}
