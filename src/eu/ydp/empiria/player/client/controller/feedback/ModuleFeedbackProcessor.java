package eu.ydp.empiria.player.client.controller.feedback;

import static com.google.common.base.Optional.fromNullable;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.module.IModule;

public class ModuleFeedbackProcessor {
	
	@Inject
	private FeedbackRegistry feedbackRegistry;
	
	private FeedbackActionCollector feedbackActionCollector;
	
	@Inject
	private FeedbackConditionMatcher matcher;
	
	private static final ImmutableList<FeedbackActionProcessor> DEFAULT_PROCESSORS = 
																	ImmutableList.<FeedbackActionProcessor>builder().
																		add(new SoundActionProcessor()).
																	build();
	
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
		FeedbackProperties properties = getPropertiesFromResponse(source);
		feedbackActionCollector.appendPropertiesToSource(properties, source);
	}
	
	private void appendActionsToSource(IModule source){
		List<Feedback> feedbackList = feedbackRegistry.getModuleFeedbacks(source);
		
		if(fromNullable(feedbackList).isPresent()){
			for(Feedback feedback: feedbackList){
				appendMatchedFeedbackActions(feedback, source);
			}
		}
	}
	
	private void appendMatchedFeedbackActions(Feedback feedback, IModule source){
		FeedbackProperties properties = feedbackActionCollector.getSourceProperties(source);
		
		if(matcher.match(feedback.getCondition(), properties)){
			feedbackActionCollector.appendActionsToSource(feedback.getActions(), source);
		}
	}
	
	private void processActions(IModule module){
		List<FeedbackActionProcessor> processors = getFeedbackProcessors(module);
		
		for(FeedbackActionProcessor processor: processors){
			if(fromNullable(processor).isPresent()){
				processor.processActions(feedbackActionCollector.getActions());
			}
		}
	}
	
	protected List<FeedbackActionProcessor> getFeedbackProcessors(IModule module){
		List<FeedbackActionProcessor> processors = Lists.newArrayList();
		
		processors.addAll(DEFAULT_PROCESSORS);
		processors.addAll(getProcessorModules(module));
		
		return processors;
	}
	
	private List<FeedbackActionProcessor> getProcessorModules(IModule module){
		//TODO: find processor in module
		return Lists.newArrayList();
	}

	private FeedbackProperties getPropertiesFromResponse(IModule module){
		return null;
	}
	
}
