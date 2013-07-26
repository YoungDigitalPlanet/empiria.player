package eu.ydp.empiria.player.client.module.choice;

import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;

public class ChoiceModuleListenerImpl implements ChoiceModuleListener {

	private final ChoiceModuleModel model;
	private final ChoiceModulePresenter presenter;

	public ChoiceModuleListenerImpl(ChoiceModuleModel model, ChoiceModulePresenter presenter) {
		this.model = model;
		this.presenter = presenter;
	}

	@Override
	public void onChoiceClick(SimpleChoicePresenter choice) {
		String choiceIdentifier = choice.getIdentifier();
		if (choice.isSelected()) {
			model.removeAnswer(choiceIdentifier);
		} else {
			model.addAnswer(choiceIdentifier);
		}

		presenter.showAnswers(ShowAnswersType.USER);
	}

}
