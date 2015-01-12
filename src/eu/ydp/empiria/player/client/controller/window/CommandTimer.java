package eu.ydp.empiria.player.client.controller.window;

import com.google.common.base.Optional;
import com.google.gwt.user.client.*;

public class CommandTimer extends Timer {

	private Optional<Command> command = Optional.absent();

	public void setCommand(Command command) {
		this.command = Optional.of(command);
	}

	@Override
	public void run() {
		if (command.isPresent()) {
			command.get().execute();
		}
	}
}
