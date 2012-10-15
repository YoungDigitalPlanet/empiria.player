package eu.ydp.empiria.player.client.module.choice;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceOption;

public interface ChoiceModulePresesenter extends ActivityPresenter{
	
	void setInlineBodyGenerator(InlineBodyGeneratorSocket bodyGenerator);
	void setPrompt(String value);
	void setChoices(List<ChoiceOption> choices);
	void switchChoiceSelection(String identifier);
	boolean isChoiceSelected(String identifier);
	Widget getFeedbackPlaceholderByIdentifier(String identifier);	
}
