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
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedbackPresenter;
import eu.ydp.gwtutil.client.StringUtils;

public class TextActionProcessor implements FeedbackActionProcessor, ActionProcessorTarget, ISimpleModule, Factory<TextActionProcessor> {

	private ActionProcessorHelper helper;
	
	private TextFeedbackPresenter feedbackPresenter = new TextFeedbackPresenter();
	
	@Inject
	Provider<TextActionProcessor> provider;
	
	public TextActionProcessor(){
		helper = new ActionProcessorHelper(this);
	}
	
	@Override
	public List<FeedbackAction> processActions(List<FeedbackAction> actions) {
		return getHelper().processActions(actions);
	}
	
	private ActionProcessorHelper getHelper(){
		if(helper == null){
			helper = new ActionProcessorHelper(this);
		}
		
		return helper;
	}

	@Override
	public boolean canProcessAction(FeedbackAction action) {
		return (action instanceof ShowTextAction && 
				!StringUtils.EMPTY_STRING.equals(((ShowTextAction) action).getText()));
	}

	@Override
	public void processSingleAction(FeedbackAction action) {
		if (action instanceof ShowTextAction) {
			ShowTextAction textAction = (ShowTextAction) action;
			feedbackPresenter.setText(textAction.getText());
			feedbackPresenter.setVisible(true);
		}
	}
	
	@Override
	public void clearFeedback() {
		feedbackPresenter.setText("");
		feedbackPresenter.setVisible(false);
	}

	@Override
	public TextActionProcessor getNewInstance() {
		return provider.get();
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener iel) {
		feedbackPresenter.setVisible(false);
	}

	@Override
	public Widget getView() {
		return feedbackPresenter;
	}

	@Override
	public List<IModule> getChildren() {
		return null;
	}

	@Override
	public HasChildren getParentModule() {
		return null;
	}

}
