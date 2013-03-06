package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class AttachHandlerImpl implements Handler {

	private AbstractHTML5MediaExecutor mediaExecutor;
	private AbstractHTML5MediaWrapper mediaWrapper;
	private boolean firstTimeInitialization = true;

	@Inject
	protected EventsBus eventsBus;

	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if (firstTimeInitialization) {
			firstTimeInitialization = false;
			return;
		}

		if (event.isAttached()) {
			HTML5VideoReattachHack html5VideoReattachHack = new HTML5VideoReattachHack();
			html5VideoReattachHack.setEventsBus(eventsBus);
			html5VideoReattachHack.reAttachVideo(mediaWrapper, mediaExecutor, this);
		}
	}

	public void setMediaExecutor(AbstractHTML5MediaExecutor mediaExecutor) {
		this.mediaExecutor = mediaExecutor;
	}

	public void setMediaWrapper(AbstractHTML5MediaWrapper mediaWrapper) {
		this.mediaWrapper = mediaWrapper;
	}

}
