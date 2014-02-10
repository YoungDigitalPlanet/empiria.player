package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder.Axis;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class RangeCreator {

	public Range<Integer> getRangeForAxis(Rectangle rectangle, Axis axis) {
		Range<Integer> range;
		if (axis == Axis.HORIZONTAL) {
			range = horizontal(rectangle);
		} else {
			range = vertical(rectangle);
		}
		return range;
	}

	private Range<Integer> horizontal(Rectangle rectangle) {
		return Ranges.closed(rectangle.getLeft(), rectangle.getRight());
	}

	private Range<Integer> vertical(Rectangle rectangle) {
		return Ranges.closed(rectangle.getTop(), rectangle.getBottom());
	}
}
