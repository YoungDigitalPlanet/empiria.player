package eu.ydp.empiria.player.client.controller.window;

import com.google.gwt.event.logical.shared.*;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

public class WindowResizeController implements ResizeHandler {

	private final CommandTimer commandTimer;

	@Inject
	public WindowResizeController(CommandTimer commandTimer, WindowResizedCommand command) {
		this.commandTimer = commandTimer;
		this.commandTimer.setCommand(command);
		Window.addResizeHandler(this);
	}

	@Override
	public void onResize(ResizeEvent event) {
		commandTimer.schedule(250);
	}
}
