package eu.ydp.empiria.player.client.module.ordering.view;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

public interface OrderInteractionViewWidget extends IsWidget {

	public abstract <W extends IsWidget> void putItemsOnView(List<W> itemsInOrder);
	public abstract void add(IsWidget widget);

}