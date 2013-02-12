package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.common.collect.Range;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.gwtutil.client.geom.Rectangle;


public class StickieViewPositionFinder {
	
	public static enum Axis {HORIZONTAL, VERTICAL};

	@Inject CenterPositionFinder positionFinder;
	@Inject RangeCreator rangeCreator;
	@Inject WidgetSizeHelper sizeHelper;
	@Inject ViewportHelper viewportHelper;

	@Inject IPlayerContainersAccessor accessor;
	
	/**
	 * Finds position for a stickie relatively to item container, taking all circumstances into consideration.
	 */
	public Integer getCenterPosition(int size, int parentAbsoluteCoord, Axis coord){
		// viewport 
		Rectangle viewport = viewportHelper.getViewport();
		Range<Integer> viewportRange = rangeForAxis(viewport, coord);
		
		// container
		Rectangle playerRect = sizeHelper.toRectange(getPlayerContainer());
		Range<Integer> containerRange = rangeForAxis(playerRect, coord);
		
		// compute
		int value = findCenterPosition(size, containerRange, viewportRange);
		
		return value - parentAbsoluteCoord;
	}
	
	private Integer findCenterPosition(int size, Range<Integer> container, Range<Integer> viewport){
		return positionFinder.findCenterPosition(size, container, viewport);
	}
	
	private IsWidget getPlayerContainer(){
		return (IsWidget)accessor.getPlayerContainer();
	}
	
	private Range<Integer> rangeForAxis(Rectangle rect, Axis ax){
		if (ax == Axis.HORIZONTAL){
			return rangeCreator.horizontal(rect);
		} else {
			return rangeCreator.vertical(rect);
		}
	}
	
	public int refinePositionHorizontal(int left, IsWidget item, IsWidget parent){
		int size = item.asWidget().getOffsetWidth();
		int min = 0;
		int max = parent.asWidget().getOffsetWidth();
		
		return refinePosition(left, size, min, max);
	}
	
	public int refinePositionVertical(int top, IsWidget item, IsWidget parent){
		int size = item.asWidget().getOffsetHeight();
		Widget container = (Widget)accessor.getPlayerContainer();
		int min = container.getAbsoluteTop() - parent.asWidget().getAbsoluteTop();
		int max = container.getAbsoluteTop() + container.getOffsetHeight() - parent.asWidget().getAbsoluteTop();
		
		return refinePosition(top, size, min, max);
	}
	
	int refinePosition(int coord, int size, int min, int max){
		
		int newCoord;
		
		if (coord < min){
			newCoord = min;
		} else if (coord > max - size){
			newCoord = max - size;
		} else {
			newCoord = coord;
		}
		
		return newCoord;
	}
	
}
