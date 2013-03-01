package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

/**
 * This hack need to be used on some iOS versions. 
 * Fixes some known issues: 
 * - video can't play first time (when embedded in Flex/AiR WebStageView), 
 * - also for page changing when video is played again.
 */
public class HTML5VideoReattachHack {

	private EventsBus eventsBus;

	public void reAttachVideo(HTML5MediaWrapper mediaWrapper, HTML5MediaExecutor mediaExecutor, AttachHandlerImpl attachHandler) {
		Video video = (Video) mediaWrapper.getMediaObject();
		Widget parent = video.getParent();
		MediaWrapper<?> eventBusSourceObject = video.getEventBusSourceObject();

		video.removeFromParent();
		Media defaultMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
		video = (Video) defaultMedia.getMedia();
		video.setEventBusSourceObject(eventBusSourceObject);

		((FlowPanel) parent).insert(video, 0);

		mediaWrapper.setMediaObject(video);
		mediaExecutor.setMediaWrapper(mediaWrapper);
		mediaExecutor.init();

		video.addAttachHandler(attachHandler);

		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, mediaWrapper), mediaWrapper);
		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_PAUSE, mediaWrapper), mediaWrapper);
	}

	public void setEventsBus(EventsBus eventsBus) {
		this.eventsBus = eventsBus;
	}
}
