package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class SurfacePositionFinder {

	private final SurfacesOffsetsUtils surfacesOffsetsUtils;
	
	@Inject
	public SurfacePositionFinder(SurfacesOffsetsUtils surfacesOffsetsUtils) {
		this.surfacesOffsetsUtils = surfacesOffsetsUtils;
	}

	public int findOffsetLeft(ConnectionItems items){
		return surfacesOffsetsUtils.findMinOffsetLeft(items);
	}
	
	public int findTopOffset(ConnectionItems items){
		return surfacesOffsetsUtils.findMinTopOffset(items);
	}
}
