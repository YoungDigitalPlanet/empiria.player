package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public interface SelectionModuleView extends IsWidget{

	void initialize(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);

	void setItemDisplayedName(XMLContent itemName, int itemNumber);

	void setChoiceOptionDisplayedName(XMLContent choiceName, int choiceNumber);

	void createButtonForItemChoicePair(int itemNumber, int choiceNumber, boolean isMulti);

	void selectButton(int itemNumber, int choiceNumber);

	void unselectButton(int itemNumber, int choiceNumber);

	void lockButton(boolean lock, int itemNumber, int choiceNumber);

	void updateButtonStyle(int itemNumber, int choiceNumber, UserAnswerType styleState);

	void addClickHandlerToButton(int itemNumber, int choiceNumber, ClickHandler clickHandler);

	void setGridSize(int amountOfItems, int amountOfChoices);
}
