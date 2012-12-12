package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.processor.ActionProcessorHelper;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionProcessorTarget;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedback;

public class ImageActionProcessor implements FeedbackActionProcessor, ActionProcessorTarget, ISimpleModule, IResetable, Factory<ImageActionProcessor> {
	
	private ActionProcessorHelper helper;
	
	@Inject
	private ImageFeedback feedbackPresenter;
	
	@Inject
	Provider<ImageActionProcessor> provider;
	
	@Override
	public List<FeedbackAction> processActions(List<FeedbackAction> actions) {
		return getHelper().processActions(actions);
	}
	
	private ActionProcessorHelper getHelper(){
		if (helper == null) {
			helper = new ActionProcessorHelper(this);
		}
		
		return helper;
	}
	
	@Override
	public boolean canProcessAction(FeedbackAction action) {
		boolean canProcess = false;
		
		if (action instanceof FeedbackUrlAction) {
			FeedbackUrlAction urlAction = (FeedbackUrlAction) action;
			canProcess = ActionType.IMAGE.equalsToString(urlAction.getType());
		}
		
		return canProcess;
	}

	@Override
	public void processSingleAction(FeedbackAction action) {
		if (action instanceof FeedbackUrlAction) {
			FeedbackUrlAction imageAction = (FeedbackUrlAction) action;
			feedbackPresenter.setUrl(imageAction.getHref());
			feedbackPresenter.show();
		}
	}
	
	@Override
	public void clearFeedback() {
		feedbackPresenter.setUrl("");
		feedbackPresenter.hide();
	}
	
	@Override
	public ImageActionProcessor getNewInstance() {
		return provider.get();
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener iel) {
		feedbackPresenter.hide();
	}
	
	@Override
	public Widget getView() {
		return (Widget) feedbackPresenter;
	}

	@Override
	public List<IModule> getChildren() {
		return null;
	}

	@Override
	public HasChildren getParentModule() {
		return null;
	}

	@Override
	public void reset() {
		clearFeedback();
	}
	
}
