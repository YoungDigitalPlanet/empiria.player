package eu.ydp.empiria.player.client.module.simulation;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.MimeUtil;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;

public class SoundJsWrapper {

	@Inject
	private MediaWrapperCreator mediaWrapperCreator;
	@Inject
	private MimeUtil mimeUtil;
	@Inject
	private MediaWrapperController mediaWrapperController;

	private final Map<String, MediaWrapper<Widget>> wrappers = new HashMap<String, MediaWrapper<Widget>>();

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

			createMediaWrapper(arg, sourcesWithTypes);
		}
	}

	private void onPlay(final String arg) {
		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		sourcesWithTypes.put(arg, mimeUtil.getMimeTypeFromExtension(arg));

		MediaWrapper<Widget> wrapper = wrappers.get(arg);

		if (wrapper != null) {
			playMediaWrapper(wrapper);
		} else {
			createMediaWrapperAndPlay(arg, sourcesWithTypes);
		}
	}

	private void createMediaWrapper(final String arg, Map<String, String> sourcesWithTypes) {
		mediaWrapperCreator.createMediaWrapper(arg, sourcesWithTypes, new CallbackRecevier<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
				wrappers.put(arg, wrapper);
			}
		});
	}

	private void createMediaWrapperAndPlay(final String arg, Map<String, String> sourcesWithTypes) {
		mediaWrapperCreator.createMediaWrapper(arg, sourcesWithTypes, new CallbackRecevier<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
				wrappers.put(arg, wrapper);
				playMediaWrapper(wrapper);
			}
		});
	}

	private void playMediaWrapper(MediaWrapper<Widget> wrapper) {
		mediaWrapperController.stopAndPlay(wrapper);
	}
}
