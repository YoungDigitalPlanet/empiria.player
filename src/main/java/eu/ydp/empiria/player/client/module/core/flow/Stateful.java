package eu.ydp.empiria.player.client.module.core.flow;

import com.google.gwt.json.client.JSONArray;

public interface Stateful {

    JSONArray getState();

    void setState(JSONArray newState);
}
