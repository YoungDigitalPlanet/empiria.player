package eu.ydp.empiria.player.client.module.choice;

import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;

public interface ChoiceModuleListener {
	
	void onChoiceClick(SimpleChoicePresenter choice);
	
}
