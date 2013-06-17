package eu.ydp.empiria.player.client.module.choice.presenter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoicePresenterFactory;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListenerImpl;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleView;
import eu.ydp.gwtutil.client.StringUtils;

public class ChoiceModulePresenterImpl implements ChoiceModulePresenter {

	private Map<String, SimpleChoicePresenter> id2choices;

	private InlineBodyGeneratorSocket bodyGenerator;

	private ChoiceInteractionBean bean;

	private ChoiceModuleModel model;

	private ChoiceModuleView view;
	
	private SimpleChoicePresenterFactory choiceModuleFactory;

	private ChoiceModuleListener listener;

	@Inject
	public ChoiceModulePresenterImpl(
			SimpleChoicePresenterFactory choiceModuleFactory,
			@ModuleScoped ChoiceModuleModel model,
			@ModuleScoped ChoiceModuleView view) {
		this.choiceModuleFactory = choiceModuleFactory;
		this.model = model;
		this.view = view;
		listener = new ChoiceModuleListenerImpl(model, this);
	}

	@Override
	public void bindView() {
		initializePrompt();
		initializeChoices();
	}

	private void initializePrompt() {
		bodyGenerator.generateInlineBody(bean.getPrompt(), view.getPrompt());
	}

	private void initializeChoices() {
		id2choices = new HashMap<String, SimpleChoicePresenter>();

		view.clear();

		for (SimpleChoiceBean choice : bean.getSimpleChoices()) {
			SimpleChoicePresenter choicePresenter = createSimpleChoicePresenter(
					choice, bodyGenerator);
			id2choices.put(choice.getIdentifier(), choicePresenter);
			view.addChoice(choicePresenter.asWidget());
			choicePresenter.setListener(listener);
		}
	}

	private SimpleChoicePresenter createSimpleChoicePresenter(
			SimpleChoiceBean choice, InlineBodyGeneratorSocket bodyGenerator) {

		return choiceModuleFactory.getSimpleChoicePresenter(choice,
				bodyGenerator);
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
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

	private Collection<SimpleChoicePresenter> getSimpleChoices() {
		return id2choices.values();
	}

	@Override
	public void reset() {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			model.removeAnswer(choiceIdentifier);

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

	@Override
	public String getChoiceIdentifier(SimpleChoicePresenter choice) {
		String searchedIdentifier = StringUtils.EMPTY_STRING;

		for (Entry<String, SimpleChoicePresenter> entry : id2choices.entrySet()) {
			if (choice.equals(entry.getValue())) {
				searchedIdentifier = entry.getKey();
				break;
			}
		}

		return searchedIdentifier;
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean mark = isChoiceMarkType(type, choiceIdentifier);

			if (choice.isSelected() && mark) {
				choice.markAnswer(type, mode);
			}
		}
	}

	private boolean isChoiceMarkType(MarkAnswersType type,
			String choiceIdentifier) {
		boolean is = false;

		if (type == MarkAnswersType.CORRECT) {
			is = model.isCorrectAnswer(choiceIdentifier);
		} else if (type == MarkAnswersType.WRONG) {
			is = model.isWrongAnswer(choiceIdentifier);
		}

		return is;
	}

	@Override
	public void showAnswers(ShowAnswersType type) {
		for (SimpleChoicePresenter choice : getSimpleChoices()) {
			String choiceIdentifier = getChoiceIdentifier(choice);
			boolean select = isChoiceAnswerType(type, choiceIdentifier);
			choice.setSelected(select);
		}
	}

	private boolean isChoiceAnswerType(ShowAnswersType type,
			String choiceIdentifier) {
		boolean select = false;

		if (type == ShowAnswersType.CORRECT) {
			select = model.isCorrectAnswer(choiceIdentifier);
		} else if (type == ShowAnswersType.USER) {
			select = model.isUserAnswer(choiceIdentifier);
		}

		return select;
	}

	@Override
	public void setBean(ChoiceInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setModel(ChoiceModuleModel model) {
		// this.model = model;
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		// TODO Auto-generated method stub
	}
}
