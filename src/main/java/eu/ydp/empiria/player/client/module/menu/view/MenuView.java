package eu.ydp.empiria.player.client.module.menu.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;

public interface MenuView extends IsWidget {
    void setTable(FlexTable table);

    void addStyleName(String style);

    void removeStyleName(String style);

    void addClickHandler(ClickHandler clickHandler);
}
