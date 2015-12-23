package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners;

public class HTML5AudioMediaExecutor extends AbstractHTML5MediaExecutor<Audio> {

    @Inject
    public HTML5AudioMediaExecutor(HTML5MediaEventMapper mediaEventMapper, HTML5MediaNativeListeners html5MediaNativeListeners) {
        super(mediaEventMapper, html5MediaNativeListeners);
    }

    @Override
    public void initExecutor() {
    }

    @Override
    protected String getMediaPreloadType() {
        return MediaElement.PRELOAD_AUTO;
    }
}
