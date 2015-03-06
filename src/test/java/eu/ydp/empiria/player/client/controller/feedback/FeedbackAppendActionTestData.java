package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.*;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;
import eu.ydp.empiria.player.client.module.IModule;
import java.util.*;
import java.util.logging.Logger;

class FeedbackAppendActionTestData {

	private final Map<Integer, FeedbackActionData> actions = Maps.newHashMap();

	void addShowUrlAction(int position, IModule module, String url, ActionType type) {
		actions.put(position, new FeedbackActionData(createUrlAction(url, type), module, ShowUrlAction.class));
	}

	void addShowTextAction(int position, IModule module, XMLContent text) {
		actions.put(position, new FeedbackActionData(createTextAction(text), module, ShowTextAction.class));
	}

	List<FeedbackAction> getModuleActions(IModule module) {
		List<FeedbackAction> moduleActions = Lists.newArrayList();

		for (FeedbackActionData actionData : actions.values()) {
			if (actionData.getModule() == module) {
				moduleActions.add(actionData.getAction());
			}
		}

		return moduleActions;
	}

	int getActionsSize() {
		return actions.values().size();
	}

	FeedbackAction getActionAtIndex(int index) {
		FeedbackAction action = null;

		try {
			action = getActionDataAtIndex(index).getAction();
		} catch (Exception exception) {
			Logger.getAnonymousLogger().info(exception.getMessage());
		}

		return action;
	}

	FeedbackActionData getActionDataAtIndex(int index) {
		FeedbackActionData actionData = null;

		try {
			actionData = actions.get(index);
		} catch (Exception exception) {
			Logger.getAnonymousLogger().info(exception.getMessage());
		}

		return actionData;
	}

	private ShowUrlAction createUrlAction(String url, ActionType type) {
		ShowUrlAction action = new ShowUrlAction();

		action.setHref(url);
		action.setType(type.getName());
		return action;
	}

	private ShowTextAction createTextAction(XMLContent text) {
		ShowTextAction action = new ShowTextAction();

		action.setContent(text);
		return action;
	}

	static class FeedbackActionData {

		private final IModule module;

		private final FeedbackAction action;

		private final Class<? extends FeedbackAction> clazz;

		public FeedbackActionData(FeedbackAction action, IModule module, Class<? extends FeedbackAction> clazz) {
			this.action = action;
			this.module = module;
			this.clazz = clazz;
		}

		public IModule getModule() {
			return module;
		}

		public FeedbackAction getAction() {
			return action;
		}

		public Class<? extends FeedbackAction> getActionClass() {
			return clazz;
		}
	}
}
