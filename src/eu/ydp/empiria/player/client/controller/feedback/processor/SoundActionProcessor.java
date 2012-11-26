package eu.ydp.empiria.player.client.controller.feedback.processor;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;

public class SoundActionProcessor extends AbstractFeedbackActionProcessor{
	
	@Inject
	private FeedbackSoundPlayer player;

	@Override
	protected boolean canProcessAction(FeedbackAction action) {
		boolean canProcess = false;
		
		if (action instanceof FeedbackUrlAction) {
			FeedbackUrlAction urlAction = (FeedbackUrlAction) action;
			canProcess = ActionType.NARRATION.equalsToString(urlAction.getType());
		}
		
		return canProcess;
	}

	@Override
	protected void processSingleAction(FeedbackAction action) {
		if (action instanceof ShowUrlAction) {
			ShowUrlAction urlAction = ((ShowUrlAction) action);
			
			if (urlAction.getSources().size() > 0) {
				player.play(urlAction.getSourcesWithTypes());
			} else {
				List<String> sources = Lists.newArrayList(urlAction.getHref());
				player.play(sources);
			}
		}
	}

	@Override
	protected void clearFeedback() {
		player.stop();
	}
	
}
