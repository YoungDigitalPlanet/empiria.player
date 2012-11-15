package eu.ydp.empiria.player.client.controller.feedback.processor;

import java.util.List;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;

public interface FeedbackActionProcessor {
	
	/**
	 * Implementation should process actions, and return list of processed actions.
	 * @param actions actions to process
	 * @return processed actions
	 */
	List<FeedbackAction> processActions(List<FeedbackAction> actions);
	
}
