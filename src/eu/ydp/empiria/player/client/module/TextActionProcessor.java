package eu.ydp.empiria.player.client.module;

import java.util.List;

import eu.ydp.empiria.player.client.controller.feedback.processor.ActionProcessorHelper;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionProcessorTarget;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;

public class TextActionProcessor implements FeedbackActionProcessor, ActionProcessorTarget, IModule {

	private ActionProcessorHelper helper;
	
	public TextActionProcessor(){
		helper = new ActionProcessorHelper(this);
	}
	
	@Override
	public List<FeedbackAction> processActions(List<FeedbackAction> actions) {
		return helper.processActions(actions);
	}

	@Override
	public boolean canProcessAction(FeedbackAction action) {
		return false;
	}

	@Override
	public void processSingleAction(FeedbackAction action) {
		
	}

	@Override
	public HasChildren getParentModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IModule> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

}
