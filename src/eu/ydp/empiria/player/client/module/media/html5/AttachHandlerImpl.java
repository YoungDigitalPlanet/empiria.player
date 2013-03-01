package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class AttachHandlerImpl implements Handler {

	private HTML5MediaExecutor mediaExecutor;
	private HTML5MediaWrapper mediaWrapper;
	private boolean firstTimeInitialization = true; 

	protected EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();

	public static AttachHandlerImpl createAttachHandlerInstance(HTML5MediaExecutor mediaExecutor, HTML5MediaWrapper mediaWrapper) {
		AttachHandlerImpl attachHandlerImpl = new AttachHandlerImpl();
		attachHandlerImpl.setMediaExecutor(mediaExecutor);
		attachHandlerImpl.setMediaWrapper(mediaWrapper);
		mediaWrapper.getMediaObject().addAttachHandler(attachHandlerImpl);
		
		return attachHandlerImpl;
	}	
	
	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if (firstTimeInitialization) {
			firstTimeInitialization = false;
			return;
		}
		
		if (!event.isAttached()) {
			return;
		}

		HTML5VideoReattachHack html5VideoReattachHack = new HTML5VideoReattachHack();
		html5VideoReattachHack.setEventsBus(eventsBus);
		html5VideoReattachHack.reAttachVideo(mediaWrapper, mediaExecutor, this);
	}

	public void setMediaExecutor(HTML5MediaExecutor mediaExecutor) {
		this.mediaExecutor = mediaExecutor;
	}
	
	public void setMediaWrapper(HTML5MediaWrapper mediaWrapper) {
		this.mediaWrapper = mediaWrapper;
	}

}
