package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class AttachHandlerImpl implements Handler {

	private MediaBase mediaBase;
	private HTML5MediaExecutor mediaExecutor;
	private HTML5MediaWrapper mediaWrapper;

	protected EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();

	@Override
	public void onAttachOrDetach(AttachEvent event) {

		if (!event.isAttached()) {
			return;
		}

		Widget videoParent = mediaBase.getParent();
		MediaWrapper<?> eventBusSourceObject = ((Video) mediaBase).getEventBusSourceObject();

		mediaBase.removeFromParent();
		Media defaultMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
		mediaBase = defaultMedia.getMedia();
		((Video) mediaBase).setEventBusSourceObject(eventBusSourceObject);

		((FlowPanel) videoParent).insert(mediaBase, 0);

		mediaWrapper.setMediaObject(mediaBase);
		mediaExecutor.setMediaWrapper(mediaWrapper);
		mediaExecutor.init();

		mediaBase.addAttachHandler(this);

		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, mediaWrapper), mediaWrapper);
		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_PAUSE, mediaWrapper), mediaWrapper);
	}

	public MediaBase getMediaBase() {
		return mediaBase;
	}

	public void setMediaBase(MediaBase mediaBase) {
		this.mediaBase = mediaBase;
	}

	public HTML5MediaExecutor getMediaExecutor() {
		return mediaExecutor;
	}

	public void setMediaExecutor(HTML5MediaExecutor mediaExecutor) {
		this.mediaExecutor = mediaExecutor;
	}

	public HTML5MediaWrapper getMediaWrapper() {
		return mediaWrapper;
	}

	public void setMediaWrapper(HTML5MediaWrapper mediaWrapper) {
		this.mediaWrapper = mediaWrapper;
	}

}
