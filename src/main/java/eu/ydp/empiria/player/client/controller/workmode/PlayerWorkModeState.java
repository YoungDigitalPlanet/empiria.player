package eu.ydp.empiria.player.client.controller.workmode;

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.json.JSONStateSerializer;
import eu.ydp.empiria.player.client.module.core.flow.Stateful;

public class PlayerWorkModeState implements Stateful {

    @Inject
    private PlayerWorkModeService playerWorkModeService;
    @Inject
    private JSONStateSerializer jsonStateSerializer;

    @Override
    public JSONArray getState() {
        PlayerWorkMode currentWorkMode = playerWorkModeService.getCurrentWorkMode();
        return jsonStateSerializer.createWithString(currentWorkMode.toString());
    }

    @Override
    public void setState(JSONArray array) {
        String state = jsonStateSerializer.extractString(array);
        playerWorkModeService.tryToUpdateWorkMode(PlayerWorkMode.valueOf(state));
    }
}
