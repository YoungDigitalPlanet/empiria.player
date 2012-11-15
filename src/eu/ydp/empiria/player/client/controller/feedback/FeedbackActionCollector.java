package eu.ydp.empiria.player.client.controller.feedback;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.module.IModule;

public class FeedbackActionCollector {
	
	private IModule source;
	
	private Map<IModule, FeedbackProperties> source2properties = Maps.newHashMap();
	
	
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

	public void appendActionsToSource(List<FeedbackAction> action, IModule source) {
		
	}

	public IModule getSource() {
		return source;
	}

	public List<FeedbackAction> getActions() {
		return null;
	}

}
