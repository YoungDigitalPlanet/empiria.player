package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;

public interface SimpleChoicePresenterFactory {

	SimpleChoicePresenter getSimpleChoicePresenter(
			SimpleChoiceBean choice,
			InlineBodyGeneratorSocket bodyGenerator);

}
