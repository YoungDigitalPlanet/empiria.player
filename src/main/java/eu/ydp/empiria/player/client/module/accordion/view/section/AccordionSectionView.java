package eu.ydp.empiria.player.client.module.accordion.view.section;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.gwtutil.client.event.factory.Command;

public interface AccordionSectionView extends IsWidget {
    void setTitle(Widget widget);

    HasWidgets getContentContainer();

    void addClickEvent(Command clickCommand);

    void hideVertically();

    void hideHorizontally();

    void showVertically();

    void showHorizontally();

    void init(Transition transition);
}
