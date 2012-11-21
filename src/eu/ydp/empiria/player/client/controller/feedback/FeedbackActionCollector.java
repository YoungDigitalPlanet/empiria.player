package eu.ydp.empiria.player.client.controller.feedback;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.module.IModule;

public class FeedbackActionCollector {
	
	private final IModule source;
	
	private final Map<IModule, FeedbackProperties> source2properties = Maps.newHashMap();
	
	private final ListMultimap<IModule, FeedbackAction> source2actions = ArrayListMultimap.create();	
	
	public FeedbackActionCollector(IModule sender){
		source = sender;
	}

	public void appendPropertiesToSource(FeedbackProperties properties, IModule source) {
		FeedbackProperties moduleProperties = source2properties.get(source);
		
		if(moduleProperties == null){
			source2properties.put(source, properties);
		}else{
			moduleProperties.appendProperties(properties);
		}
	}

	public FeedbackProperties getSourceProperties(IModule source) {
		return source2properties.get(source);
	}

	public void appendActionsToSource(List<FeedbackAction> actions, IModule source) {
		if(source2actions.containsKey(source)){
			source2actions.get(source).addAll(actions);
		}else{
			source2actions.putAll(source, actions);
		}
	}
	
	public void removeActions(List<FeedbackAction> actionsToRemove){
		source2actions.values().removeAll(actionsToRemove);
	}

	public IModule getSource() {
		return source;
	}

	public List<FeedbackAction> getActions() {
		return Lists.newArrayList(source2actions.values());
	}

	public List<FeedbackAction> getActionsForSource(IModule source) {
		return Lists.newArrayList(source2actions.get(source));
	}

}
