package eu.ydp.empiria.player.client.module.simulation.soundjs;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.module.media.MimeSourceProvider;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class SoundJsPlugin implements SoundApiForJs {

	private final MediaWrapperCreator mediaWrapperCreator;
	private final MediaWrapperController mediaWrapperController;
	private final MimeSourceProvider mimeSourceProvider;
	private final SoundJsNative soundJsNative;

	private final Map<String, MediaWrapper<Widget>> wrappers = new HashMap<>();

	@Inject
	public SoundJsPlugin(MediaWrapperCreator mediaWrapperCreator, MediaWrapperController mediaWrapperController, MimeSourceProvider mimeSourceProvider,
			SoundJsNative soundJsNative) {
		this.mediaWrapperCreator = mediaWrapperCreator;
		this.mediaWrapperController = mediaWrapperController;
		this.mimeSourceProvider = mimeSourceProvider;
		this.soundJsNative = soundJsNative;
		this.soundJsNative.setApiForJs(this);
	}

	@Override
	public void preload(final String src) {
		if (!wrappers.containsKey(src)) {
			createMediaWrapper(src, addWrapper(src));
		}
	}

	@Override
	public void play(final String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		if (wrapper == null) {
			createMediaWrapper(src, addWrapperAndPlay(src));
		} else {
			playMediaWrapper(wrapper);
		}
	}

	@Override
	public void playLooped(String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		if (wrapper == null) {
			createMediaWrapper(src, addWrapperAndPlayLooped(src));
		} else {
			playLoopedMediaWrapper(wrapper);
		}
	}

	@Override
	public void stop(String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		mediaWrapperController.stop(wrapper);
	}

	@Override
	public void pause(String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		mediaWrapperController.pause(wrapper);
	}

	@Override
	public void resume(String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		mediaWrapperController.resume(wrapper);
	}

	private void createMediaWrapper(final String src, CallbackReceiver<MediaWrapper<Widget>> receiver) {
		Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypeByExtension(src);
		mediaWrapperCreator.createSimulationMediaWrapper(src, sourcesWithTypes, receiver);
	}

	private void playMediaWrapper(MediaWrapper<Widget> wrapper) {
		mediaWrapperController.stopAndPlay(wrapper);
	}

	private void playLoopedMediaWrapper(MediaWrapper<Widget> wrapper) {
		mediaWrapperController.stopAndPlayLooped(wrapper);
	}

	private void addOnEndHandler(final MediaWrapper<Widget> wrapper) {
		mediaWrapperController.addHandler(MediaEventTypes.ON_END, wrapper, onEndHandler);
	}

	private final MediaEventHandler onEndHandler = new MediaEventHandler() {
		@Override
		public void onMediaEvent(MediaEvent event) {
			if (MediaEventTypes.ON_END == event.getType()) {
				AbstractHTML5MediaWrapper html5MediaWrapper = (AbstractHTML5MediaWrapper) event.getMediaWrapper();
				String src = html5MediaWrapper.getMediaBase().getCurrentSrc();

				soundJsNative.onComplete(src);
			}
		}
	};

	private CallbackReceiver<MediaWrapper<Widget>> addWrapper(final String src) {
		return new SoundJsCallbackReceiver(src);
	}

	private CallbackReceiver<MediaWrapper<Widget>> addWrapperAndPlay(final String src) {
		return new SoundJsCallbackReceiver(src) {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
				super.setCallbackReturnObject(wrapper);
				playMediaWrapper(wrapper);
			}

		};
	}

	private CallbackReceiver<MediaWrapper<Widget>> addWrapperAndPlayLooped(final String src) {
		return new SoundJsCallbackReceiver(src) {
			@Override
			public void setCallbackReturnObject(final MediaWrapper<Widget> wrapper) {
				super.setCallbackReturnObject(wrapper);
				playLoopedMediaWrapper(wrapper);
			}
		};
	}

	private class SoundJsCallbackReceiver implements CallbackReceiver<MediaWrapper<Widget>> {

		private final String src;

		public SoundJsCallbackReceiver(String src) {
			this.src = src;
		}

		@Override
		public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
			addOnEndHandler(wrapper);
			wrappers.put(src, wrapper);
		}
	}
}
