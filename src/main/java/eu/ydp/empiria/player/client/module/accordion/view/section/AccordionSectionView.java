package eu.ydp.empiria.player.client.module.accordion.view.section;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.gwtutil.client.event.factory.Command;

public interface AccordionSectionView extends IsWidget {
    void setTitle(Widget widget);

    HasWidgets getContentContainer();

    void addClickCommand(Command clickCommand);

    void addSectionStyleName(String style);

    void addContentWrapperStyleName(String style);

    void removeSectionStyleName(String style);

    void removeContentWrapperStyleName(String style);

    int getContentHeight();

    int getContentWidth();

    void setSectionDimensions(String width, String height);
}
