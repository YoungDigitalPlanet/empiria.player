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
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class SoundJsPlugin implements SoundApiForJs {

	private final MediaWrapperCreator mediaWrapperCreator;
	private final MediaWrapperController mediaWrapperController;
	private final MimeSourceProvider mimeSourceProvider;
	private final SoundJsNative soundJsNative;
	private final EventsBus eventsBus;

	private final Map<String, MediaWrapper<Widget>> wrappers = new HashMap<>();

	@Inject
	public SoundJsPlugin(MediaWrapperCreator mediaWrapperCreator, MediaWrapperController mediaWrapperController, MimeSourceProvider mimeSourceProvider,
			SoundJsNative soundJsNative, EventsBus eventsBus) {
		this.mediaWrapperCreator = mediaWrapperCreator;
		this.mediaWrapperController = mediaWrapperController;
		this.mimeSourceProvider = mimeSourceProvider;
		this.soundJsNative = soundJsNative;
		this.soundJsNative.setApiForJs(this);
		this.eventsBus = eventsBus;
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
	public void stop(String src) {
		MediaWrapper<Widget> wrapper = wrappers.get(src);
		mediaWrapperController.stop(wrapper);
	}

	private void createMediaWrapper(final String src, CallbackRecevier<MediaWrapper<Widget>> receiver) {
		Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypeByExtension(src);
		mediaWrapperCreator.createSimulationMediaWrapper(src, sourcesWithTypes, receiver);
	}

	private void playMediaWrapper(MediaWrapper<Widget> wrapper) {
		mediaWrapperController.stopAndPlay(wrapper);
	}

	private CallbackRecevier<MediaWrapper<Widget>> addWrapper(final String src) {
		CallbackRecevier<MediaWrapper<Widget>> receiver = new CallbackRecevier<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
				addOnEndHandler(wrapper);
				wrappers.put(src, wrapper);
			}

		};
		return receiver;
	}

	private CallbackRecevier<MediaWrapper<Widget>> addWrapperAndPlay(final String src) {
		CallbackRecevier<MediaWrapper<Widget>> receiver = new CallbackRecevier<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(final MediaWrapper<Widget> wrapper) {
				addOnEndHandler(wrapper);
				wrappers.put(src, wrapper);
				playMediaWrapper(wrapper);
			}
		};
		return receiver;
	}

	private void addOnEndHandler(MediaWrapper<Widget> wrapper) {
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), wrapper, onEndHandler);
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
}
