package eu.ydp.empiria.player.client.controller.feedback.structure.action;


public interface ActionProcessorTarget{
	
	boolean canProcessAction(FeedbackAction action);
	
	void processSingleAction(FeedbackAction action);
	
	void clearFeedback();
	
}
