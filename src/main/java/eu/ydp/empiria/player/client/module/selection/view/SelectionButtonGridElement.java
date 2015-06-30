package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.event.dom.client.ClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public interface SelectionButtonGridElement extends SelectionGridElement {

    void addClickHandler(ClickHandler clickHandler);

    void select();

    void unselect();

    void setButtonEnabled(boolean b);

    void updateStyle();

    void updateStyle(UserAnswerType styleState);
}
