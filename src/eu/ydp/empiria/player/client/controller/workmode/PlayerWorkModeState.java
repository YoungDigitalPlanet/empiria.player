package eu.ydp.empiria.player.client.controller.workmode;

import com.google.common.base.Optional;
import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.json.JSONStateSerializer;
import eu.ydp.empiria.player.client.module.IStateful;

public class PlayerWorkModeState implements IStateful {

	@Inject
	private PlayerWorkModeService playerWorkModeService;
	@Inject
	private JSONStateSerializer jsonStateSerializer;

	private Optional<PlayerWorkMode> workModeFromState = Optional.absent();

	@Override
	public JSONArray getState() {
		PlayerWorkMode currentWorkMode = playerWorkModeService.getCurrentWorkMode();
		return jsonStateSerializer.createWithString(currentWorkMode.toString());
	}

	@Override
	public void setState(JSONArray array) {
		String state = jsonStateSerializer.extractString(array);
		workModeFromState = Optional.of(PlayerWorkMode.valueOf(state));
	}

	public void updateWorkModeFormState() {
		if (workModeFromState.isPresent()) {
			playerWorkModeService.tryToUpdateWorkMode(workModeFromState.get());
		}
	}
}
