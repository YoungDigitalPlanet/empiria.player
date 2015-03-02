package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.name.Named;

import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;
import eu.ydp.empiria.player.client.module.choice.providers.SimpleChoiceStyleProvider;
import eu.ydp.empiria.player.client.module.choice.view.SimpleChoiceView;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

public interface SimpleChoiceViewFactory {

	SimpleChoiceView getSimpleChoiceView(SimpleChoicePresenter simpleChoicePresenter, SimpleChoiceStyleProvider styleProvider);

	@Named("multi")
	ChoiceButtonBase getMultiChoiceButton(String styleName);

	@Named("single")
	ChoiceButtonBase getSingleChoiceButton(String styleName);

	@Named("multi")
	SimpleChoiceStyleProvider getMultiChoiceStyleProvider();

	@Named("single")
	SimpleChoiceStyleProvider getSingleChoiceStyleProvider();
}
