package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.*;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import java.util.*;

public class SlideshowSounds {

	private final Map<String, MediaWrapper<Widget>> sounds = Maps.newHashMap();
	
	private final MediaWrapperCreator mediaWrapperCreator;
	private final MimeSourceProvider mimeSourceProvider;

	@Inject
	public SlideshowSounds(MediaWrapperCreator slideshowMediaWrapperCreator, MimeSourceProvider mimeSourceProvider) {
		this.mediaWrapperCreator = slideshowMediaWrapperCreator;
		this.mimeSourceProvider = mimeSourceProvider;
	}

	public MediaWrapper<Widget> getSound(String audiopath) {
		return sounds.get(audiopath);
	}

	public void initSound(String audiopath) {
		if (!sounds.containsKey(audiopath)) {
			createMediaWrapper(audiopath);
		}
	}

	public boolean containsWrapper(MediaWrapper<Widget> mediaWrapper) {
		return sounds.containsValue(mediaWrapper);
	}

	public Collection<MediaWrapper<Widget>> getAllSounds() {
		return sounds.values();
	}

	private void createMediaWrapper(String audiopath) {
		Map<String, String> sourceWithType = mimeSourceProvider.getSourcesWithTypeByExtension(audiopath);
		CallbackReceiver<MediaWrapper<Widget>> callbackReceiver = createCallbackReceiver(audiopath);

		mediaWrapperCreator.createMediaWrapper(audiopath, sourceWithType, callbackReceiver);
	}

	private CallbackReceiver<MediaWrapper<Widget>> createCallbackReceiver(final String audiopath) {
		return new CallbackReceiver<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mediaWrapper) {
				sounds.put(audiopath, mediaWrapper);
			}
		};
	}
}