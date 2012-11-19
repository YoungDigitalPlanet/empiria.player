package eu.ydp.empiria.player.client.controller.feedback.processor;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;

public class SoundActionProcessor extends AbstractFeedbackActionProcessor{

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
		// TODO Auto-generated method stub
	}
	
}
