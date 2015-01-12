package eu.ydp.empiria.player.client.controller.window;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.*;

public class WindowResizedCommand implements Command {

	private final PlayerEvent windowResized = new PlayerEvent(PlayerEventTypes.WINDOW_RESIZED);

	@Inject
	private EventsBus eventsBus;

	@Override
	public void execute() {
		eventsBus.fireAsyncEvent(windowResized);
	}
}
