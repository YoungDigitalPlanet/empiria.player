package eu.ydp.empiria.player.client.controller.feedback.processor;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;

public class SoundActionProcessor extends AbstractFeedbackActionProcessor{

	@Override
	protected boolean canProcessAction(FeedbackAction action) {
		boolean canProcess = false;
		
		try{
			FeedbackUrlAction urlAction = (FeedbackUrlAction)action;
			canProcess = "mp3".equals(urlAction.getType());//FIXME: typy wrzucić do enuma
		}catch(ClassCastException exception){// NOPMD by MKaldonek on 14.11.12 15:17 
						
		}
		
		return canProcess;
	}

	@Override
	protected void processSingleAction(FeedbackAction action) {
		// TODO Auto-generated method stub
	}
	
}
