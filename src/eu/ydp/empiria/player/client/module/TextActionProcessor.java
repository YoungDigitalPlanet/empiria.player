package eu.ydp.empiria.player.client.module;

import java.util.List;

import eu.ydp.empiria.player.client.controller.feedback.processor.ActionProcessorHelper;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionProcessorTarget;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.gwtutil.client.StringUtils;

public class TextActionProcessor implements FeedbackActionProcessor, ActionProcessorTarget, IModule {

	private ActionProcessorHelper helper;
	
	@Override
	public List<FeedbackAction> processActions(List<FeedbackAction> actions) {
		return getHelper().processActions(actions);
	}
	
	private ActionProcessorHelper getHelper(){
		if(helper == null){
			helper = new ActionProcessorHelper(this);
		}
		
		return helper;
	}

	@Override
	public boolean canProcessAction(FeedbackAction action) {
		return (action instanceof ShowTextAction && 
				!StringUtils.EMPTY_STRING.equals(((ShowTextAction) action).getText()));
	}

	@Override
	public void processSingleAction(FeedbackAction action) {
		System.out.println("this: " + this);
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
