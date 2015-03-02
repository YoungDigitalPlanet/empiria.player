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
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.gwtutil.client.StringUtils;

public class TextActionProcessor implements FeedbackActionProcessor, ActionProcessorTarget, ISimpleModule, IResetable, Factory<TextActionProcessor> {

	private ActionProcessorHelper helper;

	@Inject
	private TextFeedback feedbackPresenter;

	@Inject
	Provider<TextActionProcessor> provider;

	@Override
	public List<FeedbackAction> processActions(List<FeedbackAction> actions) {
		return getHelper().processActions(actions);
	}

	private ActionProcessorHelper getHelper() {
		if (helper == null) {
			helper = new ActionProcessorHelper(this);
		}

		return helper;
	}

	@Override
	public boolean canProcessAction(FeedbackAction action) {
		boolean canProcess = false;

		if (action instanceof ShowTextAction) {
			ShowTextAction textAction = (ShowTextAction) action;
			canProcess = !StringUtils.EMPTY_STRING.equals(textAction.getText());
		}

		return canProcess;
	}

	@Override
	public void processSingleAction(FeedbackAction action) {
		if (action instanceof ShowTextAction) {
			ShowTextAction textAction = (ShowTextAction) action;
			feedbackPresenter.setText(textAction.getText());
			feedbackPresenter.show();
		}
	}

	@Override
	public void clearFeedback() {
		feedbackPresenter.setText("");
		feedbackPresenter.hide();
	}

	@Override
	public TextActionProcessor getNewInstance() {
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
