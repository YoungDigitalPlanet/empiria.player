package eu.ydp.empiria.player.client.module.identification;

import com.google.common.collect.*;
import com.google.gwt.json.client.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.module.identification.predicates.*;
import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;
import java.util.List;

public class IdentificationChoicesManager {

	private final List<SelectableChoicePresenter> choices = Lists.newArrayList();

	@Inject
	private ChoiceToIdentifierTransformer choiceToIdentifierTransformer;
	@Inject
	private SelectedChoicePredicate selectedChoicePredicate;

	public void addChoice(SelectableChoicePresenter choice) {
		choices.add(choice);
	}

	public void lockAll() {
		for (SelectableChoicePresenter choice : choices) {
			choice.lock();
		}
	}

	public void unlockAll() {
		for (SelectableChoicePresenter choice : choices) {
			choice.unlock();
		}
	}

	public void markAnswers(boolean mark, CorrectAnswers correctAnswers) {
		for (SelectableChoicePresenter choice : choices) {
			boolean correct = correctAnswers.containsAnswer(choice.getIdentifier());
			choice.markAnswers(mark, correct);
		}
	}

	public void clearSelections() {
		for (SelectableChoicePresenter choice : choices) {
			choice.setSelected(false);
		}
	}

	public void restoreView(List<String> selectedChoices) {
		for (SelectableChoicePresenter choice : choices) {
			boolean select = selectedChoices.contains(choice.getIdentifier());
			choice.setSelected(select);
		}
	}

	public void selectCorrectAnswers(CorrectAnswers correctAnswers) {
		for (SelectableChoicePresenter choice : choices) {
			boolean select = correctAnswers.containsAnswer(choice.getIdentifier());
			choice.setSelected(select);
		}
	}

	public JSONArray getState() {
		JSONArray array = new JSONArray();
		for (int i = 0; i < choices.size(); i++) {
			boolean selected = choices.get(i).isSelected();
			array.set(i, JSONBoolean.getInstance(selected));
		}

		return array;
	}

	public void setState(JSONArray newState) {
		for (int i = 0; i < choices.size(); i++) {
			JSONValue value = newState.get(i);
			if (value != null) {
				boolean selected = value.isBoolean().booleanValue();
				choices.get(i).setSelected(selected);
			}
		}
	}

	public List<SelectableChoicePresenter> getSelectedChoices() {
		return FluentIterable.from(choices)
				.filter(selectedChoicePredicate)
				.toList();
	}

	public List<String> getIdentifiersSelectedChoices() {
		return FluentIterable.from(choices)
				.filter(selectedChoicePredicate)
				.transform(choiceToIdentifierTransformer)
				.toList();
	}
}
