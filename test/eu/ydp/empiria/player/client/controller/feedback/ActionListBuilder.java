package eu.ydp.empiria.player.client.controller.feedback;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;

public class ActionListBuilder {
	
	private List<FeedbackAction> actions;
	
	private ActionListBuilder(){
		actions = Lists.newArrayList();
	}
	
	public static ActionListBuilder create(){
		return new ActionListBuilder();
	}
	
	public ActionListBuilder addUrlAction(ActionType type, String href){
		ShowUrlAction action = new ShowUrlAction();
		
		action.setType(type.getName());
		action.setHref(href);
		actions.add(action);
		
		return this;
	}
	
	public ActionListBuilder addTextAction(String text){
		ShowTextAction action = new ShowTextAction();
		
		action.setText(text);
		actions.add(action);
		
		return this;
	}
	
	public List<FeedbackAction> getList(){
		return ImmutableList.copyOf(actions);
	}
	
}
