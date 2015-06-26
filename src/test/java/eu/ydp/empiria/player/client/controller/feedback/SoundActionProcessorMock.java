package eu.ydp.empiria.player.client.controller.feedback;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public class SoundActionProcessorMock extends SoundActionProcessor {

	@Inject
	public SoundActionProcessorMock(EventsBus eventsBus) {
		super(eventsBus);
	}

	@Override
	protected void processSingleAction(FeedbackAction action) {
		super.processSingleAction(action);
	}

}
