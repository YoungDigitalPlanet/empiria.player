package eu.ydp.empiria.player.client.animation.preload;

import eu.ydp.empiria.player.client.util.geom.Size;

public interface ImagePreloadHandler {

	void onLoad(Size imageSize);
	void onError();
}
