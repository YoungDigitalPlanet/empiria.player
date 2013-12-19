package eu.ydp.empiria.player.client.module.ordering.view;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

public interface OrderInteractionViewWidget extends IsWidget {

	<W extends IsWidget> void putItemsOnView(List<W> itemsInOrder);

	void add(IsWidget widget);

	void setOrientation(OrderInteractionOrientation orientation);

	String getId();
}