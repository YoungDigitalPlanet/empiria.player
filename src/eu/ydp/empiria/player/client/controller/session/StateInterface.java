package eu.ydp.empiria.player.client.controller.session;

import com.google.gwt.json.client.JSONArray;

public interface StateInterface {

	public void setState(JSONArray state);
	public JSONArray getState();
}
