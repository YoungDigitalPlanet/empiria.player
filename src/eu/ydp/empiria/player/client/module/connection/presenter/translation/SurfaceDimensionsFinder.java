package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;
import eu.ydp.empiria.player.client.module.view.HasDimensions;

public class SurfaceDimensionsFinder {

	@Inject
	private SurfacesOffsetsUtils surfacesOffsetsUtils;

	public int findHeight(HasDimensions view, ConnectionItems items) {
		if(canDimensionsBeCalculated(items)){
			return findHeight(items);
		} else {
			return view.getHeight();
		}
	}

	private int findHeight(ConnectionItems items) {
		int maxOffsetTop = surfacesOffsetsUtils.findMaxTopOffset(items);
		int minOffsetTop = surfacesOffsetsUtils.findMinTopOffset(items);
		
		int heightOfElement = findHeightOfAnyElement(items);
		
		return maxOffsetTop - minOffsetTop +heightOfElement;
	}

	private int findHeightOfAnyElement(ConnectionItems items) {
		ConnectionItem item = getFirstItem(items);
		return item.getHeight();
	}

	public int findWidth(HasDimensions view, ConnectionItems items) {
		if (canDimensionsBeCalculated(items)) {
			return calculateWidth(items);
		} else {
			return view.getWidth();
		}
	}

	private boolean canDimensionsBeCalculated(ConnectionItems items) {
		return !items.getLeftItems()
				.isEmpty() && !items.getRightItems()
				.isEmpty();
	}

	private int calculateWidth(ConnectionItems items) {
		checkArgument(!items.getLeftItems().isEmpty());
		checkArgument(!items.getRightItems().isEmpty());

		int width = findWidthBasedOnMostLeftAndMostRightItems(items);

		return width;
	}

	private int findWidthBasedOnMostLeftAndMostRightItems(ConnectionItems items) {
		int minOffsetLeft = surfacesOffsetsUtils.findMinOffsetLeft(items);
		int maxOffsetLeft = surfacesOffsetsUtils.findMaxOffsetLeft(items);

		int widthOfElement = getWidthOfFirstElement(items);
		return maxOffsetLeft - minOffsetLeft + widthOfElement;
	}

	private int getWidthOfFirstElement(ConnectionItems items) {
		ConnectionItem item = getFirstItem(items);
		return item.getWidth();
	}

	private ConnectionItem getFirstItem(ConnectionItems items) {
		return items.getAllItems().iterator().next();
	}
}
