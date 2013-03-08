package eu.ydp.empiria.player.client.module.media.html5.reattachhack;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.AttachHandlerFactory;
import eu.ydp.empiria.player.client.module.media.html5.AttachHandlerImpl;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
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
	
	@Inject
	private	HTML5VideoRebuilder videoRebuilder;

	private Video video; 
	
	public void reAttachVideo(HTML5VideoMediaWrapper mediaWrapper, HTML5VideoMediaExecutor mediaExecutor) {
		AttachHandlerImpl attachHandler = attachHandlerFactory.createAttachHandler(mediaExecutor, mediaWrapper);
		reAttachVideo(mediaWrapper, mediaExecutor, attachHandler);
	}
	
	public void reAttachVideo(HTML5VideoMediaWrapper mediaWrapper, HTML5VideoMediaExecutor mediaExecutor, AttachHandlerImpl attachHandler) {
		videoRebuilder.recreateVideoWidget(mediaWrapper);
		video = videoRebuilder.getVideo();
		updateMediaExecutor(mediaExecutor, mediaWrapper);		
		addAttachHandler(video, attachHandler);		
		fireEvents(mediaWrapper);
	}

	private void addAttachHandler(Video video, AttachHandlerImpl attachHandler) {
		video.addAttachHandler(attachHandler);
	}

	private void updateMediaExecutor(HTML5VideoMediaExecutor mediaExecutor, AbstractHTML5MediaWrapper mediaWrapper) {
		mediaExecutor.setMediaWrapper(mediaWrapper);
		mediaExecutor.init();
	}

	private void fireEvents(AbstractHTML5MediaWrapper mediaWrapper) {
		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, mediaWrapper), mediaWrapper);
		eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_PAUSE, mediaWrapper), mediaWrapper);
	}	

}
