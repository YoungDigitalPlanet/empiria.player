package eu.ydp.empiria.player.client.module;

import com.google.gwt.json.client.JSONArray;

public interface IStateful {

    JSONArray getState();

    void setState(JSONArray newState);
}
