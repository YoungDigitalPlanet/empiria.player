package eu.ydp.empiria.player.client.module.ordering.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

import java.util.List;

public interface OrderInteractionViewWidget extends IsWidget {

    <W extends IsWidget> void putItemsOnView(List<W> itemsInOrder);

    void add(IsWidget widget);

    void setOrientation(OrderInteractionOrientation orientation);

    String getMainPanelUniqueCssClass();
}
