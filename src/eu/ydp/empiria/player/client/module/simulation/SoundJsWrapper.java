package eu.ydp.empiria.player.client.module.simulation;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.dictionary.external.MediaWrapperController;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.MimeUtil;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;

public class SoundJsWrapper {

	@Inject
	private MediaWrapperCreator mediaWrapperCreator;
	@Inject
	private MimeUtil mimeUtil;
	@Inject
	private MediaWrapperController mediaWrapperController;

	private final Map<String, MediaWrapper> wrappers = new HashMap<String, MediaWrapper>();

	public SoundJsWrapper() {
		nativeInit();
	}

	private native void nativeInit()/*-{
									var instance = this;
									$wnd.empiriaSoundJsBeginPlaying = function (arg) {
										instance.@eu.ydp.empiria.player.client.module.simulation.SoundJsWrapper::onPlay(Ljava/lang/String;)(arg);
										
									}
									$wnd.empiriaSoundJsPreload = function (arg) {
										instance.@eu.ydp.empiria.player.client.module.simulation.SoundJsWrapper::preload(Ljava/lang/String;)(arg);
									}
									}-*/;

	private void preload(final String arg) {
		if (!wrappers.containsKey(arg)) {
			Map<String, String> sourcesWithTypes = Maps.newHashMap();
			sourcesWithTypes.put(arg, mimeUtil.getMimeTypeFromExtension(arg));

			mediaWrapperCreator.createMediaWrapper(arg, sourcesWithTypes, new CallbackRecevier<MediaWrapper<Widget>>() {
				@Override
				public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
					wrappers.put(arg, wrapper);
				}
			});
		}
	}

	private void onPlay(final String arg) {
		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		sourcesWithTypes.put(arg, mimeUtil.getMimeTypeFromExtension(arg));

		MediaWrapper wrapper = wrappers.get(arg);

		if (wrapper != null) {
			playMediaWrapper(wrapper);
		} else {
			mediaWrapperCreator.createMediaWrapper(arg, sourcesWithTypes, new CallbackRecevier<MediaWrapper<Widget>>() {
				@Override
				public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
					wrappers.put(arg, wrapper);
					playMediaWrapper(wrapper);
				}
			});
		}
	}

	private void playMediaWrapper(MediaWrapper<Widget> wrapper) {
		mediaWrapperController.play(wrapper);
	}
}
