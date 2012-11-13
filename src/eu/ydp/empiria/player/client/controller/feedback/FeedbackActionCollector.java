package eu.ydp.empiria.player.client.controller.feedback;

import java.util.List;

import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackAction;
import eu.ydp.empiria.player.client.module.IModule;

public class FeedbackActionCollector {
	
	private IModule source;
	
	
	public FeedbackActionCollector(IModule sender){
		source = sender;
	}

	public void appendPropertiesToSource(FeedbackProperties properties, IModule source) {
		
	}

	public FeedbackProperties getSourceProperties(IModule source) {
		return null;
	}

	public void appendActionsToSource(List<FeedbackAction> actions, IModule source) {
		
	}

	public IModule getSource() {
		return source;
	}

}
