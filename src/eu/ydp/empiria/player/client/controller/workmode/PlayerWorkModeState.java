package eu.ydp.empiria.player.client.controller.workmode;

import com.google.common.base.Optional;
import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.json.JSONStateUtil;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

import javax.annotation.PostConstruct;

public class PlayerWorkModeState implements IStateful {

	@Inject
	private EventsBus eventsBus;
	@Inject
	private PlayerWorkModeService playerWorkModeService;
	@Inject
	private JSONStateUtil jsonStateUtil;

	private Optional<PlayerWorkMode> workModeFromState = Optional.absent();

	@PostConstruct
	public void postConstruct() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), new PlayerEventHandler() {
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if (workModeFromState.isPresent()) {
					playerWorkModeService.tryToUpdateWorkMode(workModeFromState.get());
				}
			}
		});
	}

	@Override
	public JSONArray getState() {
		PlayerWorkMode currentWorkMode = playerWorkModeService.getCurrentWorkMode();
		return jsonStateUtil.createWithString(currentWorkMode.toString());
	}

	@Override
	public void setState(JSONArray array) {
		String state = jsonStateUtil.extractString(array);
		workModeFromState = Optional.of(PlayerWorkMode.valueOf(state));
	}
}
