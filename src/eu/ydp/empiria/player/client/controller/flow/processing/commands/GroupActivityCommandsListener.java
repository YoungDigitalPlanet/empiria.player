package eu.ydp.empiria.player.client.controller.flow.processing.commands;

import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;

public interface GroupActivityCommandsListener {

	public void checkGroup(FlowProcessingEventsListener fpel);
	public void continueGroup(FlowProcessingEventsListener fpel);
}
