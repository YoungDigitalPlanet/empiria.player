package eu.ydp.empiria.player.client.controller.feedback.processor;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;

public class SoundActionProcessor extends AbstractFeedbackActionProcessor{
	
	@Inject
	private SoundPlayer player;

	@Override
	protected boolean canProcessAction(FeedbackAction action) {
		boolean canProcess = false;
		
		try{
			FeedbackUrlAction urlAction = (FeedbackUrlAction)action;
			canProcess = ActionType.NARRATION.equalsToString(urlAction.getType());
		}catch(ClassCastException exception){// NOPMD by MKaldonek on 14.11.12 15:17 
						
		}
		
		return canProcess;
	}

	@Override
	protected void processSingleAction(FeedbackAction action) {
		if (action instanceof ShowUrlAction) {
			ShowUrlAction urlAction = ((ShowUrlAction) action);
			
			List<String> sources = Lists.newArrayList();
			sources.add(urlAction.getHref());
			
			player.play(sources);
		}
	}
	
}
