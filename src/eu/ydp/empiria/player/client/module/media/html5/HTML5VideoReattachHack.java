package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
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

	@Inject
	private EventsBus eventsBus;

	@Inject
	private AttachHandlerFactory attachHandlerFactory;
	
	public void reAttachVideo(HTML5VideoMediaWrapper mediaWrapper, HTML5VideoMediaExecutor mediaExecutor) {
		AttachHandlerImpl attachHandler = attachHandlerFactory.createAttachHandler(mediaExecutor, mediaWrapper);
		reAttachVideo(mediaWrapper, mediaExecutor, attachHandler);
	}
	
	public void reAttachVideo(AbstractHTML5MediaWrapper mediaWrapper, HTML5VideoMediaExecutor mediaExecutor, AttachHandlerImpl attachHandler) {
		Video video = (Video) mediaWrapper.getMediaObject();
		Widget parentWidget = video.getParent();
		MediaWrapper<?> eventBusSourceObject = video.getEventBusSourceObject();

		video.removeFromParent();		
		video = creatNewDefaultVideoAndSetSourceObject(eventBusSourceObject);
		
		insertVideoAndUpdateMediaWrapper(mediaWrapper, video, parentWidget);		
		updateMediaExecutor(mediaExecutor, mediaWrapper);

		video.addAttachHandler(attachHandler);
		
		fireEvents(mediaWrapper);
	}

	private void updateMediaExecutor(HTML5VideoMediaExecutor mediaExecutor, AbstractHTML5MediaWrapper mediaWrapper) {
		mediaExecutor.setMediaWrapper(mediaWrapper);
		mediaExecutor.init();
	}

	private void insertVideoAndUpdateMediaWrapper(AbstractHTML5MediaWrapper mediaWrapper, Video video, Widget parent) {
		((FlowPanel) parent).insert(video, 0);		
		mediaWrapper.setMediaObject(video);
	}

	private Video creatNewDefaultVideoAndSetSourceObject(MediaWrapper<?> eventBusSourceObject) {		
		Media defaultMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
		Video video = (Video) defaultMedia.getMedia();
		video.setEventBusSourceObject(eventBusSourceObject);
		return video;
	}

	private void fireEvents(AbstractHTML5MediaWrapper mediaWrapper) {
		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, mediaWrapper), mediaWrapper);
		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_PAUSE, mediaWrapper), mediaWrapper);
	}

}
