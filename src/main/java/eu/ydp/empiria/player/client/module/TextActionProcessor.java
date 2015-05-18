package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.*;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.processor.*;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.gwtutil.client.StringUtils;
import java.util.List;

public class TextActionProcessor implements FeedbackActionProcessor, ActionProcessorTarget, ISimpleModule, IResetable, Factory<TextActionProcessor> {

	private ActionProcessorHelper helper;

	@Inject
	private TextFeedback feedbackPresenter;

	@Inject
	private Provider<TextActionProcessor> provider;
	private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

	@Override
	public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
		return getHelper().processActions(actions, inlineBodyGeneratorSocket);
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
			String nodeValue = textAction.getContent().getValue().getChildNodes().toString();
			canProcess = !StringUtils.EMPTY_STRING.equals(nodeValue);
		}

		return canProcess;
	}

	@Override
	public void processSingleAction(FeedbackAction action) {
		if (action instanceof ShowTextAction) {
			ShowTextAction textAction = (ShowTextAction) action;
			Element element = textAction.getContent().getValue();
			Widget widget = inlineBodyGeneratorSocket.generateInlineBody(element);
			feedbackPresenter.setTextElement(widget);
			feedbackPresenter.show();
		}
	}

	@Override
	public void clearFeedback() {
		feedbackPresenter.clearTextElement();
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
