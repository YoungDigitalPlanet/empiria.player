package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;

public class HTML5AudioMediaExecutor extends AbstractHTML5MediaExecutor<Audio> {

	@Override
	public void initExecutor() {
	}

	@Override
	protected String getMediaPreloadType() {
		return MediaElement.PRELOAD_AUTO;
	}
}
