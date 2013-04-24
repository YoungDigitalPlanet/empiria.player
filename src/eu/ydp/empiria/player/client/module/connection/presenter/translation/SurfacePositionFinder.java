package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import java.util.Collection;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class SurfacePositionFinder {

	public int findOffsetLeft(ConnectionItems items){
		Collection<ConnectionItem> leftItems = items.getLeftItems();
		
		if (leftItems.isEmpty()){
			return 0;
		}
		
		ConnectionItem firstLeftItem = leftItems.iterator().next();
		return firstLeftItem.getOffsetLeft();
		
	}
}
