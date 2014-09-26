package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.module.media.MimeSourceProvider;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;

import java.util.HashMap;
import java.util.Map;

public class SoundJsPlugin implements ApiForJs {

	private MediaWrapperCreator mediaWrapperCreator;
	private MediaWrapperController mediaWrapperController;
	private MimeSourceProvider mimeSourceProvider;
	private SoundJsNative soundJsNative;

	private final Map<String, MediaWrapper<Widget>> wrappers = new HashMap<>();

	@Inject
	public SoundJsPlugin(MediaWrapperCreator mediaWrapperCreator, MediaWrapperController mediaWrapperController, MimeSourceProvider mimeSourceProvider, SoundJsNative soundJsNative) {
		this.mediaWrapperCreator = mediaWrapperCreator;
		this.mediaWrapperController = mediaWrapperController;
		this.mimeSourceProvider = mimeSourceProvider;
		this.soundJsNative = soundJsNative;

		soundJsNative.setApiForJS(this);
	}

	public void preload(final String src) {
		if (!wrappers.containsKey(src)) {
			createMediaWrapper(src, addWrapper(src));
		}
	}

	public void play(final String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		if (wrapper == null) {
			createMediaWrapper(src, addWrapperAndPlay(src));
		} else {
			playMediaWrapper(wrapper);
		}
	}

	private void createMediaWrapper(final String src, CallbackRecevier<MediaWrapper<Widget>> receiver) {
		Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypeByExtension(src);
		mediaWrapperCreator.createMediaWrapper(src, sourcesWithTypes, receiver);
	}

	private void playMediaWrapper(MediaWrapper<Widget> wrapper) {
		mediaWrapperController.stopAndPlay(wrapper);
	}

	private CallbackRecevier<MediaWrapper<Widget>> addWrapper(final String src) {
		CallbackRecevier<MediaWrapper<Widget>> receiver = new CallbackRecevier<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
				wrappers.put(src, wrapper);
			}
		};
		return receiver;
	}

	private CallbackRecevier<MediaWrapper<Widget>> addWrapperAndPlay(final String src) {
		CallbackRecevier<MediaWrapper<Widget>> receiver = new CallbackRecevier<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
				wrappers.put(src, wrapper);
				playMediaWrapper(wrapper);
			}
		};
		return receiver;
	}

	public void stop(String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		mediaWrapperController.stop(wrapper);
	}
}
