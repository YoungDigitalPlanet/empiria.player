package eu.ydp.empiria.player.client.util.events.command;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;

import eu.ydp.gwtutil.client.event.Event;

public class FireCommand<H,E extends Event<H, ?>> implements ScheduledCommand{
	private final H handler;
	private final E event;
	public FireCommand( H handler,E event) {
		this.event = event;
		this.handler = handler;
	}
	@Override
	public void execute() {
		this.event.dispatch(handler);
	}
}
