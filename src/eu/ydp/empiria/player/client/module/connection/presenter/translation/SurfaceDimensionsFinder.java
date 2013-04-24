package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;
import eu.ydp.empiria.player.client.module.view.HasDimensions;

public class SurfaceDimensionsFinder {

	public int findHeight(HasDimensions view) {
		return view.getHeight();
	}

	public int findWidth(HasDimensions view, ConnectionItems items) {
		if (widthCanBeCalculated(items)){
			return calculateWidth(items); 
		} else {
			return view.getWidth();
		}
	}

	private boolean widthCanBeCalculated(ConnectionItems items) {
		return !items.getLeftItems().isEmpty()  &&  !items.getRightItems().isEmpty();
	}

	private int calculateWidth(ConnectionItems items) {
		checkArgument(!items.getLeftItems().isEmpty());
		checkArgument(!items.getRightItems().isEmpty());
		
		Collection<ConnectionItem> leftItems = items.getLeftItems();
		Collection<ConnectionItem> rightItems = items.getRightItems();
		
		ConnectionItem left0 = leftItems.iterator().next();
		ConnectionItem right0 = rightItems.iterator().next();
		
		return calculateWidthForLeftAndRightItem(left0, right0);
	}

	private int calculateWidthForLeftAndRightItem(ConnectionItem left0, ConnectionItem right0) {
		int rightX = right0.getOffsetLeft();
		int leftX = left0.getOffsetLeft();
		int leftWidth = left0.getWidth();
		
		return rightX - leftX + leftWidth;
	}
	
}
