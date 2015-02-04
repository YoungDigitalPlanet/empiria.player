package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.*;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import java.util.*;

public class SlideSounds {

	private final Map<String, MediaWrapper<Widget>> sounds = Maps.newHashMap();
	
	private final MediaWrapperCreator mediaWrapperCreator;
	private final MimeSourceProvider mimeSourceProvider;

	@Inject
	public SlideSounds(MediaWrapperCreator slideshowMediaWrapperCreator, MimeSourceProvider mimeSourceProvider) {
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

	public boolean containsWrapper(MediaWrapper<Widget> mw) {
		return sounds.containsValue(mw);
	}

	public Collection<MediaWrapper<Widget>> getAllSounds() {
		return sounds.values();
	}

	private void createMediaWrapper(String audiopath) {
		Map<String, String> sourceWithType = mimeSourceProvider.getSourcesWithTypeByExtension(audiopath);
		CallbackReceiver<MediaWrapper<Widget>> cr = createCallbackReceiver(audiopath);

		mediaWrapperCreator.createMediaWrapper(audiopath, sourceWithType, cr);
	}

	private CallbackReceiver<MediaWrapper<Widget>> createCallbackReceiver(final String audiopath) {
		return new CallbackReceiver<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mw) {
				sounds.put(audiopath, mw);
			}
		};
	}
}