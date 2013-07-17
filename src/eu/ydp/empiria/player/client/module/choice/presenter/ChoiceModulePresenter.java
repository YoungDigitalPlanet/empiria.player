package eu.ydp.empiria.player.client.module.choice.presenter;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;

public interface ChoiceModulePresenter extends ActivityPresenter<ChoiceModuleModel, ChoiceInteractionBean> {

	void setInlineBodyGenerator(InlineBodyGeneratorSocket bodyGenerator);

	IsWidget getFeedbackPlaceholderByIdentifier(String identifier);

	String getChoiceIdentifier(SimpleChoicePresenter choice);
}
