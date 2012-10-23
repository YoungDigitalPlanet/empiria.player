package eu.ydp.empiria.player.client.module.choice.presenter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.gwtutil.client.StringUtils;

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

	private Map<String, SimpleChoicePresenter> id2choices;

	private InlineBodyGeneratorSocket bodyGenerator;

	private ChoiceInteractionBean bean;

	private ChoiceModuleModel model;

	private final ChoiceModuleListener listener = new ChoiceModuleListener() {

		@Override
		public void onChoiceClick(SimpleChoicePresenter choice) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			if(choice.isSelected()){
				model.unselectResponse(choiceIdentifier);
			}else{
				model.selectResponse(choiceIdentifier);
			}

			showCurrentAnswers();
		}
	};

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

		id2choices = new HashMap<String, SimpleChoicePresenter>();
		ChoiceGroupController groupController = new ChoiceGroupController();

		for (SimpleChoiceBean choice : bean.getSimpleChoices()) {
			SimpleChoicePresenter choicePresenter = createSimpleChoicePresenter(choice, groupController, bodyGenerator);
			id2choices.put(choice.getIdentifier(), choicePresenter);
			choicesPanel.add(choicePresenter.asWidget());
			choicePresenter.setListener(listener);
		}
	}

	private SimpleChoicePresenter createSimpleChoicePresenter(SimpleChoiceBean choice, ChoiceGroupController groupController, InlineBodyGeneratorSocket bodyGenerator){
		return new SimpleChoicePresenterImpl(choice, groupController, bodyGenerator);
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
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			choice.setLocked(locked);
		}
	}

	private Collection<SimpleChoicePresenter> getSimpleChoices(){
		return id2choices.values();
	}

	@Override
	public void reset() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			choice.reset();
		}
	}

	@Override
	public IsWidget getFeedbackPlaceholderByIdentifier(String identifier) {
		IsWidget placeholder = null;

		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			if (identifier.equals(choiceIdentifier)) {
				placeholder = choice.getFeedbackPlaceHolder();
				break;
			}
		}

		return placeholder;
	}

	private String getChoiceIdentifier(SimpleChoicePresenter choice){
		String searchedIdentifier = StringUtils.EMPTY_STRING;

		for(Entry<String, SimpleChoicePresenter> entry: id2choices.entrySet()){
			if(choice.equals(entry.getValue())){
				searchedIdentifier = entry.getKey();
				break;
			}
		}

		return searchedIdentifier;
	}

	@Override
	public void markCorrectAnswers() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean isCorrect = model.isCorrectAnswer(choiceIdentifier);
			if (isCorrect && choice.isSelected()) {
				choice.markCorrectAnswers();
			}
		}
	}

	@Override
	public void markWrongAnswers() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean isWrong = model.isWrongAnswer(choiceIdentifier);
			if(isWrong && choice.isSelected()){
				choice.markWrongAnswers();
			}
		}
	}

	@Override
	public void unmarkCorrectAnswers() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean isCorrect = model.isCorrectAnswer(choiceIdentifier);
			if(isCorrect && choice.isSelected()){
				choice.unmarkCorrectAnswers();
			}
		}
	}

	@Override
	public void unmarkWrongAnswers() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean isWrong = model.isWrongAnswer(choiceIdentifier);
			if(isWrong && choice.isSelected()){
				choice.unmarkWrongAnswers();
			}
		}
	}

	@Override
	public void showCorrectAnswers() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean isCorrect = model.isCorrectAnswer(choiceIdentifier);
			choice.setSelected(isCorrect);
		}
	}

	@Override
	public void showCurrentAnswers() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean isCurrent = model.isCurrentAnswer(choiceIdentifier);
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

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		// TODO Auto-generated method stub

	}

}
