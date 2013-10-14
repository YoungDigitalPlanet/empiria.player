package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;

public class FeedbackActionConditions {

	@Inject
	private OutcomeAccessor outcomeAccessor;

	public boolean isPageAllOk() {
		int todo = outcomeAccessor.getCurrentPageTodo();
		int done = outcomeAccessor.getCurrentPageDone();
		return todo > 0 && todo == done;
	}

	public boolean isPageAllOkWithoutPreviousErrors() {
		boolean withoutMistakes = outcomeAccessor.getCurrentPageErrors() == 0;
		return withoutMistakes && isPageAllOk();
	}
}
