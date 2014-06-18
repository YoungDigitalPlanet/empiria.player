package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;

import java.util.*;

public class FeedbackActionCollector {

	private IModule source;

	private final Map<IModule, FeedbackProperties> source2properties = Maps.newHashMap();

	private final ListMultimap<IModule, FeedbackAction> source2actions = ArrayListMultimap.create();

	public void setSource(IModule source) {
		this.source = source;
	}

	public void appendPropertiesToSource(FeedbackProperties properties, IModule source) {
		FeedbackProperties moduleProperties = source2properties.get(source);

		if (moduleProperties == null) {
			source2properties.put(source, properties);
		} else {
			moduleProperties.appendProperties(properties);
		}
	}

	public FeedbackProperties getSourceProperties(IModule source) {
		return source2properties.get(source);
	}

	public void appendActionsToSource(List<FeedbackAction> actions, IModule source) {
		if (source2actions.containsKey(source)) {
			source2actions.get(source).addAll(actions);
		} else {
			source2actions.putAll(source, actions);
		}
		clearChildActions(source, actions);
	}

	private void clearChildActions(IModule currentModule, List<FeedbackAction> currentActions) {
		Set<Class> classesToRemove = getCurrentActionsClasses(currentActions);
		List<IModule> sourcesToClear = getSourcesToClear(currentModule);
		for (IModule sourceToRemove : sourcesToClear) {
			List<FeedbackAction> actionsConsideredToRemove = source2actions.get(sourceToRemove);
			List<FeedbackAction> actionsToRemove = getActionsToRemove(actionsConsideredToRemove, classesToRemove);
			actionsConsideredToRemove.removeAll(actionsToRemove);
		}
	}

	private Set<Class> getCurrentActionsClasses(List<FeedbackAction> currentActions) {
		Set<Class> classesToRemove = new HashSet<>();
		for (FeedbackAction currentAction : currentActions) {
			classesToRemove.add(currentAction.getClass());
		}
		return classesToRemove;
	}

	private List<IModule> getSourcesToClear(IModule currentModule) {
		List<IModule> keysToRemove = new ArrayList<>();
		for (IModule source : source2actions.keySet()) {
			List<HasChildren> parents = getParentHierarchy(source);
			if (parents.contains(currentModule)) {
				keysToRemove.add(source);
			}
		}
		return keysToRemove;
	}

	private List<FeedbackAction> getActionsToRemove(List<FeedbackAction> actionsConsideredToRemove, Set<Class> classesToRemove) {
		List<FeedbackAction> actionsToRemove = new ArrayList<>();
		for (FeedbackAction actionConsideredToRemove : actionsConsideredToRemove) {
			if (classesToRemove.contains(actionConsideredToRemove.getClass())) {
				actionsToRemove.add(actionConsideredToRemove);
			}
		}
		return actionsToRemove;
	}

	private List<HasChildren> getParentHierarchy(IModule source) {
		List<HasChildren> parents = new ArrayList<>();
		HasChildren parent = source.getParentModule();
		while (parent != null) {
			parents.add(parent);
			parent = parent.getParentModule();
		}
		return parents;
	}

	public void removeActions(List<FeedbackAction> actionsToRemove) {
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
