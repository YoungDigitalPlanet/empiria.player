package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class SurfacesOffsetsCalculator {

	@Inject
	private SurfacesOffsetsExtractor offsetsExtractor;

	public int findMinOffsetLeft(ConnectionItems items) {
		Collection<Integer> leftOffsets = offsetsExtractor.extractLeftOffsets(items);

		return Collections.min(leftOffsets);
	}

	public int findMaxOffsetLeft(ConnectionItems items) {
		Collection<Integer> leftOffsets = offsetsExtractor.extractLeftOffsets(items);

		return Collections.max(leftOffsets);
	}

	public int findMinOffsetTop(ConnectionItems items) {
		Collection<Integer> topOffsets = offsetsExtractor.extractTopOffsets(items);

		return Collections.min(topOffsets);
	}

	public int findMaxOffsetTop(ConnectionItems items) {
		Collection<Integer> topOffsets = offsetsExtractor.extractTopOffsets(items);

		return Collections.max(topOffsets);
	}

	public int findMaxOffsetRight(ConnectionItems items) {
		Collection<Integer> rightOffsets = offsetsExtractor.extractRightOffsets(items);

		return Collections.max(rightOffsets);
	}

	public int findMaxOffsetBottom(ConnectionItems items) {
		Collection<Integer> bottomOffsets = offsetsExtractor.extractBottomOffsets(items);

		return Collections.max(bottomOffsets);
	}
}
