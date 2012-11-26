package eu.ydp.empiria.player.client.controller.feedback;

import static com.google.common.base.Optional.fromNullable;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IUniqueModule;

public class ModuleFeedbackProcessor {
	
	@Inject
	private FeedbackRegistry feedbackRegistry;
	
	@Inject
	protected FeedbackActionCollector feedbackActionCollector;
	
	@Inject
	private FeedbackConditionMatcher matcher;
	
	@Inject
	private FeedbackPropertiesCreator propertiesCreator;
	
	private Map<String, ? extends Variable> variables;

	@Inject
	private SoundActionProcessor soundProcessor;

	public void process(IModule sender, Map<String, ? extends Variable> variables){
		feedbackActionCollector.setSource(sender);
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
	
	protected void processActions(IModule module){
		List<FeedbackActionProcessor> processors = getFeedbackProcessors(module);
		
		for(FeedbackActionProcessor processor: processors){
			if(fromNullable(processor).isPresent()){
				List<FeedbackAction> processedActions = processor.processActions(feedbackActionCollector.getActions());
				feedbackActionCollector.removeActions(processedActions);
			}
		}
	}
	
	protected List<FeedbackActionProcessor> getFeedbackProcessors(IModule module){
		List<FeedbackActionProcessor> processors = Lists.newArrayList();
		
		processors.addAll(getDefaultProcessors());
		processors.addAll(getProcessorModules(module));
		
		return processors;
	}
	
	private List<FeedbackActionProcessor> getDefaultProcessors() {
		List<FeedbackActionProcessor> processors = Lists.newArrayList();
		processors.add(soundProcessor);
		
		return processors;
	}
	
	protected List<FeedbackActionProcessor> getProcessorModules(IModule module){
		List<FeedbackActionProcessor> processors = Lists.newArrayList();
		
		try {
			IModule parentModule = module.getParentModule();
			
			for (IModule child : parentModule.getChildren()) {
				if(child instanceof FeedbackActionProcessor){
					processors.add((FeedbackActionProcessor) child);
				}
			}
		} catch (Exception exception) {
			Logger.getLogger(getClass().getName()).info(exception.getMessage());
		}
		
		return processors;
	}

	private FeedbackProperties getPropertiesFromResponse(IModule module){
		FeedbackProperties properties = new FeedbackProperties();
		
		if(module instanceof IUniqueModule){
			String identifier = ((IUniqueModule) module).getIdentifier();
			properties = propertiesCreator.getPropertiesFromVariables(identifier, variables);
		}
		
		return properties;
	}
	
}
