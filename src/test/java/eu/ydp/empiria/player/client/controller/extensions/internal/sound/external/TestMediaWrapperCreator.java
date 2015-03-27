package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.ClassUtil;
import eu.ydp.gwtutil.client.Wrapper;

public class TestMediaWrapperCreator {

	private EventsBus eventsBus;

	public void init(EventsBus eventsBus) {
		this.eventsBus = eventsBus;
	}

	public List<MediaWrapper<Widget>> createMediaWrappers(Iterable<? extends TestMedia> testMedias) {
		List<MediaWrapper<Widget>> wrappers = Lists.newArrayList();
		for (TestMedia media : testMedias) {
			wrappers.add(createMediaWrapper(media));
		}
		return wrappers;
	}

	public MediaWrapper<Widget> createMediaWrapper() {
		final Wrapper<MediaWrapper<Widget>> mwWrapper = Wrapper.ofStrict(ClassUtil.<MediaWrapper<Widget>> castClassUnsafe(MediaWrapper.class));
		CallbackReceiver<MediaWrapper<Widget>> callbackRecevier = new CallbackReceiver<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> o) {
				mwWrapper.setInstance(o);
			}
		};
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, TestMediaAudio.MULTI_MP3_OGG.getMediaConfiguration(), callbackRecevier));
		return mwWrapper.getInstance();
	}

	public MediaWrapper<Widget> createMediaWrapper(TestMedia testMedias) {
		final Wrapper<MediaWrapper<Widget>> mwWrapper = Wrapper.ofStrict(ClassUtil.<MediaWrapper<Widget>> castClassUnsafe(MediaWrapper.class));
		CallbackReceiver<MediaWrapper<Widget>> callbackRecevier = new CallbackReceiver<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> o) {
				mwWrapper.setInstance(o);
			}
		};
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, testMedias.getMediaConfiguration(), callbackRecevier));
		return mwWrapper.getInstance();
	}
}
