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

	private List<SimpleChoiceView> choiceViews;

	private InlineBodyGeneratorSocket bodyGenerator;
	
	private ChoiceInteractionBean bean;
	
	private ChoiceModuleModel model;

	@Override
	public void bindView() {
		uiBinder.createAndBindUi(this);
		initializePrompt();
		initializeChoices();
	}
	
	private void initializePrompt() {
		bodyGenerator.generateInlineBody(bean.getPrompt(), promptWidget.getElement());
	}
	
	private void initializeChoices() {
		choicesPanel.clear();

		choiceViews = new ArrayList<SimpleChoiceView>();
		ChoiceGroupController groupController = new ChoiceGroupController();

		for (SimpleChoiceBean choice : bean.getSimpleChoices()) {
			SimpleChoiceView choiceView = new SimpleChoiceView(choice, groupController, bodyGenerator);
			choiceViews.add(choiceView);
			choicesPanel.add(choiceView.asWidget());
		}
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	@Override
	public void setInlineBodyGenerator(InlineBodyGeneratorSocket bodyGenerator) {
		this.bodyGenerator = bodyGenerator;
	}
	
	public void showAnswers(List<String> answers) {
		for (SimpleChoiceView choice : choiceViews) {
			boolean select = answers.contains(choice.getIdentifier());
			choice.setSelected(select);
		}
	}

	@Override
	public void setLocked(boolean locked) {
		for (SimpleChoiceView choice : choiceViews) {
			choice.setLocked(locked);
		}
	}

	@Override
	public void reset() {
		for (SimpleChoiceView choice : choiceViews) {
			choice.reset();
		}
	}

	@Override
	public void switchChoiceSelection(String identifier) {
		for (SimpleChoiceView choice : choiceViews) {
			if (identifier.equals(choice.getIdentifier())) {
				choice.setSelected(!choice.isSelected());
			}
		}
	}

	@Override
	public boolean isChoiceSelected(String identifier) {
		boolean selected = false;

		try {
			selected = getChoiceByIdentifier(identifier).isSelected();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return selected;
	}

	private SimpleChoiceView getChoiceByIdentifier(String identifier) {
		SimpleChoiceView searchedChoice = null;

		for (SimpleChoiceView choice : choiceViews) {
			if (identifier.equals(choice.getIdentifier())) {
				searchedChoice = choice;
				break;
			}
		}

		return searchedChoice;
	}

	@Override
	public void markCorrectAnswers() {
		for (SimpleChoiceView choice : choiceViews) {
			// boolean markCorrect =
			// correctAnswers.contains(choice.getIdentifier());
			// choice.markAnswers(mark, markCorrect);
		}
	}
	
	@Override
	public void markWrongAnswers() {

	}

	@Override
	public void unmarkCorrectAnswers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unmarkWrongAnswers() {

	}

	@Override
	public Widget getFeedbackPlaceholderByIdentifier(String identifier) {
		Widget placeholder = null;

		for (SimpleChoiceView choice : choiceViews) {
			if (identifier.equals(choice.getIdentifier())) {
				placeholder = choice.getFeedbackPlaceHolder();
				break;
			}
		}

		return placeholder;
	}

	@Override
	public void showCorrectAnswers() {
		
	}

	@Override
	public void showCurrentAnswers() {
		
	}

	@Override
	public void setBean(ChoiceInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setModel(ChoiceModuleModel model) {
		// TODO Auto-generated method stub
		
	}
}
