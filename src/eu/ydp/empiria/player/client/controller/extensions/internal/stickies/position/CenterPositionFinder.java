package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.common.collect.Range;

public class CenterPositionFinder {

	/**
	 * Finds coordinate center position in absolute coordinates, concerning the container, viewport that clips the container and the size of the item.
	 */
	public int findCenterPosition(Integer itemSize, Range<Integer> container, Range<Integer> viewport){
		int result;
		if (itemSize > container.upperEndpoint() - container.lowerEndpoint()){
			result = container.lowerEndpoint();
		} else {
			int min = Math.max(container.lowerEndpoint(), viewport.lowerEndpoint());
			int max = Math.min(container.upperEndpoint(), viewport.upperEndpoint());
			if (min + itemSize > max){
				if (container.upperEndpoint() < viewport.lowerEndpoint()){ // viewport above container
					result = container.upperEndpoint() - itemSize;
				} else {
					result = container.lowerEndpoint();
				}
			} else {
				result = min + (max - min - itemSize) / 2;
			}
		}
		return result;
	}
}