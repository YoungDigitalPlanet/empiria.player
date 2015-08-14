package eu.ydp.empiria.player.client.module.menu.view;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;

public interface MenuView extends IsWidget {
    void setTable(FlexTable table);

    void addStyleName(String style);

    void removeStyleName(String style);

    void addClickHandler(EventHandlerProxy userClickHandler);
}
