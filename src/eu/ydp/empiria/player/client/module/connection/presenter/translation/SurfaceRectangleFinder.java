package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class SurfaceRectangleFinder {

	private final SurfacesOffsetsCalculator surfacesOffsetsUtils;

	@Inject
	public SurfaceRectangleFinder(SurfacesOffsetsCalculator surfacesOffsetsUtils) {
		this.surfacesOffsetsUtils = surfacesOffsetsUtils;
	}

	public int findOffsetLeft(ConnectionItems items) {
		return surfacesOffsetsUtils.findMinOffsetLeft(items);
	}

	public int findOffsetTop(ConnectionItems items) {
		return surfacesOffsetsUtils.findMinOffsetTop(items);
	}

	public int findWidth(ConnectionItems items) {
		final int minLeft = surfacesOffsetsUtils.findMinOffsetLeft(items);
		final int maxRight = surfacesOffsetsUtils.findMaxOffsetRight(items);
	
		return maxRight - minLeft;
	}

	public int findHeight(ConnectionItems items) {
		final int minTop = surfacesOffsetsUtils.findMinOffsetTop(items);
		final int maxBottom = surfacesOffsetsUtils.findMaxOffsetBottom(items);
		
		return maxBottom - minTop;
	}
}
