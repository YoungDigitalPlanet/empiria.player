package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class HTML5AudioMediaExecutor extends AbstractHTML5MediaExecutor<Audio> {

	@Inject
	public HTML5AudioMediaExecutor(HTML5MediaEventMapper mediaEventMapper, HTML5MediaNativeListeners html5MediaNativeListeners, EventsBus eventsBus) {
		super(mediaEventMapper, html5MediaNativeListeners, eventsBus);
	}

	@Override
	public void initExecutor() {
	}

	@Override
	protected String getMediaPreloadType() {
		return MediaElement.PRELOAD_AUTO;
	}
}
