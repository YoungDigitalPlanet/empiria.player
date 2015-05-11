package eu.ydp.empiria.player.client.module.external.sound;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;

import java.util.HashMap;
import java.util.Map;

public class ExternalInteractionSoundWrappers {

	@Inject
	private MediaWrapperCreator mediaWrapperCreator;

	private final Map<String, MediaWrapper<Widget>> wrappers = new HashMap<>();

	public void execute(String src, MediaWrapperAction action) {
		if (wrappers.containsKey(src)) {
			action.execute(wrappers.get(src));
		} else {
			mediaWrapperCreator.createMediaWrapper(src, createCallback(src, action));
		}
	}

	private CallbackReceiver<MediaWrapper<Widget>> createCallback(final String src, final MediaWrapperAction action) {
		return new CallbackReceiver<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> wrapper) {
				wrappers.put(src, wrapper);
				action.execute(wrapper);
			}
		};
	}
}
