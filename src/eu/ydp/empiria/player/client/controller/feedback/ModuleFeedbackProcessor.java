package eu.ydp.empiria.player.client.controller.feedback;

import static com.google.common.base.Optional.fromNullable;

import java.util.List;

import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackAction;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.IModule;

public class ModuleFeedbackProcessor {
	
	private FeedbackRegistry feedbackRegistry = PlayerGinjector.INSTANCE.getFeedbackRegistry();
	
	private FeedbackActionCollector feedbackActionCollector;
	
	private FeedbackCriterionMatcher matcher;
	
	public void process(IModule sender){
		feedbackActionCollector = new FeedbackActionCollector(sender);
		processFeedbackActionCollector(sender);
	}
	
	private void processFeedbackActionCollector(IModule source){
		if(fromNullable(source).isPresent()){
			appendPropertiesToSource(source);
			appendActionsToSource(source);
			processActions(source);
			
			processFeedbackActionCollector(source.getParentModule());
		}		
	}
	
	private void appendPropertiesToSource(IModule source) {
		Object properties = getPropertiesFromResponse(source);
		feedbackActionCollector.appendPropertiesToSource(properties, source);
	}
	
	private void appendActionsToSource(IModule source){
		List<Feedback> feedbackList = feedbackRegistry.getModuleFeedbacks(source);
		
		if(fromNullable(feedbackList).isPresent()){
			for(Feedback feedback: feedbackList){
				appendFeedbackActions(feedback, source);
			}
		}
	}
	
	private void appendFeedbackActions(Feedback feedback, IModule source){
		Object properties = feedbackActionCollector.getSourceProperties(source);
		
		if(matcher.match(feedback.getCriterion(), properties)){
			feedbackActionCollector.appendActionsToSource(feedback.getActions(), source);
		}
	}
	
	private void processActions(IModule module){
		FeedbackActionProcessor processor = getFeedbackProcessor(module);
		
		if(fromNullable(processor).isPresent()){
			//processor.processActions();
		}
	}
	
	private FeedbackActionProcessor getFeedbackProcessor(IModule module){
		//TODO: find processor in module
		return new FeedbackActionProcessor() {
			
			@Override
			public void processActions(List<FeedbackAction> actions) {
								
			}
		};
	}

	private Object getPropertiesFromResponse(IModule module){
		return null;
	}
	
}
