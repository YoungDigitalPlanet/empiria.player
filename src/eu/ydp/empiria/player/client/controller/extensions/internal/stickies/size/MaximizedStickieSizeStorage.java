package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

public class MaximizedStickieSizeStorage {

	private final Map<Integer, StickieSize> colorToStickieSizeMap = new HashMap<Integer, StickieSize>();

	public Optional<StickieSize> getSizeOfMaximizedStickie(int colorIndex) {
		StickieSize stickieSize = colorToStickieSizeMap.get(colorIndex);
		return Optional.fromNullable(stickieSize);
	}

	public void updateIfBiggerThanExisting(int colorIndex, StickieSize size) {
		StickieSize existingStickieSize = colorToStickieSizeMap.get(colorIndex);

		StickieSize mergedSize;
		if (existingStickieSize == null) {
			mergedSize = size;
		} else {
			mergedSize = mergeSizes(size, existingStickieSize);
		}

		colorToStickieSizeMap.put(colorIndex, mergedSize);
	}

	private StickieSize mergeSizes(StickieSize size, StickieSize existingStickieSize) {
		int maxHeight = Math.max(size.getHeight(), existingStickieSize.getHeight());
		int maxWidth = Math.max(size.getWidth(), existingStickieSize.getWidth());
		StickieSize mergedSize = new StickieSize(maxWidth, maxHeight);
		return mergedSize;
	}
}
