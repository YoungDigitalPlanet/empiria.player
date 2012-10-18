package eu.ydp.empiria.player.client.module.choice;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;

public class ChoiceModulePresenterImpl implements ChoiceModulePresenter {

	@UiTemplate("ChoiceModuleView.ui.xml")
	interface ChoiceModuleUiBinder extends UiBinder<Widget, ChoiceModulePresenterImpl> {
	};

	private final ChoiceModuleUiBinder uiBinder = GWT.create(ChoiceModuleUiBinder.class);

	@UiField
	Panel mainPanel;

	@UiField
	Widget promptWidget;

	@UiField
	Panel choicesPanel;

	private List<SimpleChoicePresenterImpl> choiceViews;

	private InlineBodyGeneratorSocket bodyGenerator;

	private ChoiceInteractionBean bean;

	private ChoiceModuleModel model;

	@Override
	public void bindView() {
		uiBinder.createAndBindUi(this);
		initializePrompt();
		initializeChoices();
		initializeListeners();
	}

	private void initializePrompt() {
		bodyGenerator.generateInlineBody(bean.getPrompt(), promptWidget.getElement());
	}

	private void initializeChoices() {
		SimpleChoiceListener listener = new SimpleChoiceListener();
		choicesPanel.clear();

		choiceViews = new ArrayList<SimpleChoicePresenterImpl>();
		ChoiceGroupController groupController = new ChoiceGroupController();

		for (SimpleChoiceBean choice : bean.getSimpleChoices()) {
			SimpleChoicePresenterImpl choiceView = new SimpleChoicePresenterImpl(choice, groupController, bodyGenerator);
			choiceViews.add(choiceView);
			choicesPanel.add(choiceView.asWidget());
			choiceView.setListener(listener);
		}
	}

	private void initializeListeners() {

	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	@Override
	public void setInlineBodyGenerator(InlineBodyGeneratorSocket bodyGenerator) {
		this.bodyGenerator = bodyGenerator;
	}

	@Override
	public void setLocked(boolean locked) {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			choice.setLocked(locked);
		}
	}

	@Override
	public void reset() {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			choice.reset();
		}
	}

	private SimpleChoicePresenterImpl getChoiceByIdentifier(String identifier) {
		SimpleChoicePresenterImpl searchedChoice = null;

		for (SimpleChoicePresenterImpl choice : choiceViews) {
			if (identifier.equals(choice.getIdentifier())) {
				searchedChoice = choice;
				break;
			}
		}

		return searchedChoice;
	}

	@Override
	public Widget getFeedbackPlaceholderByIdentifier(String identifier) {
		Widget placeholder = null;

		for (SimpleChoicePresenterImpl choice : choiceViews) {
			if (identifier.equals(choice.getIdentifier())) {
				placeholder = choice.getFeedbackPlaceHolder();
				break;
			}
		}

		return placeholder;
	}

	@Override
	public void markCorrectAnswers() {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			boolean isCorrect = model.isCorrectAnswer(choice.getIdentifier());
			if (isCorrect && choice.isSelected()) {
				choice.markCorrectAnswers();
			}
		}
	}

	@Override
	public void markWrongAnswers() {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			boolean isWrong = model.isWrongAnswer(choice.getIdentifier());
			if(isWrong && choice.isSelected()){
				choice.markWrongAnswers();
			}
		}
	}

	@Override
	public void unmarkCorrectAnswers() {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			boolean isCorrect = model.isCorrectAnswer(choice.getIdentifier());
			if(isCorrect && choice.isSelected()){
				choice.unmarkCorrectAnswers();
			}
		}
	}

	@Override
	public void unmarkWrongAnswers() {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			boolean isWrong = model.isWrongAnswer(choice.getIdentifier());
			if(isWrong && choice.isSelected()){
				choice.unmarkWrongAnswers();
			}
		}
	}

	@Override
	public void showCorrectAnswers() {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			boolean isCorrect = model.isCorrectAnswer(choice.getIdentifier());
			choice.setSelected(isCorrect);
		}
	}

	@Override
	public void showCurrentAnswers() {
		for (SimpleChoicePresenterImpl choice : choiceViews) {
			boolean isCurrent = model.isCurrentAnswer(choice.getIdentifier());
			choice.setSelected(isCurrent);
		}
	}

	@Override
	public void setBean(ChoiceInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setModel(ChoiceModuleModel model) {
		this.model = model;
	}
	
	public class SimpleChoiceListener{
		
		public void onChoiceClick(SimpleChoicePresenterImpl choice){
			
			if(!choice.isSelected()){
				model.selectResponse(choice.getIdentifier());
			}else{
				model.unselectResponse(choice.getIdentifier());
			}
			
			showCurrentAnswers();
			//fireStateChanged(true);
		}
	}

}
