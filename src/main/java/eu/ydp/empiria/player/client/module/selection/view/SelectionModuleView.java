package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public interface SelectionModuleView extends IsWidget {

    void initialize(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);

    void setItemDisplayedName(XMLContent itemName, SelectionGridElementPosition gridElementPosition);

    void setChoiceOptionDisplayedName(XMLContent choiceName, SelectionGridElementPosition gridElementPosition);

    void createButtonForItemChoicePair(SelectionGridElementPosition gridElementPosition, String moduleStyleName);

    void selectButton(SelectionGridElementPosition gridElementPosition);

    void unselectButton(SelectionGridElementPosition gridElementPosition);

    void lockButton(SelectionGridElementPosition gridElementPosition, boolean lock);

    void updateButtonStyle(SelectionGridElementPosition gridElementPosition, UserAnswerType styleState);

    void addClickHandlerToButton(SelectionGridElementPosition gridElementPosition, ClickHandler clickHandler);

    void setGridSize(int amountOfItems, int amountOfChoices);
}
