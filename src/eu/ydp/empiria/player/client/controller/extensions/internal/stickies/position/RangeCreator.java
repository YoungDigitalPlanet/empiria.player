package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import eu.ydp.gwtutil.client.geom.Rectangle;

public class RangeCreator {
	
	public Range<Integer> horizontal(Rectangle rectangle){
		return Ranges.closed(rectangle.getLeft(), rectangle.getRight());
	}
	
	public Range<Integer> vertical(Rectangle rectangle){
		return Ranges.closed(rectangle.getTop(), rectangle.getBottom());
	}
}
