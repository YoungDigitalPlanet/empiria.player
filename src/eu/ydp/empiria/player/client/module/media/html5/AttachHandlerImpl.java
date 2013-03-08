package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.module.media.html5.reattachhack.HTML5VideoReattachHack;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class AttachHandlerImpl implements Handler {

	private HTML5VideoMediaExecutor mediaExecutor;
	private HTML5VideoMediaWrapper mediaWrapper;
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
			html5VideoReattachHack.reAttachVideo(mediaWrapper, mediaExecutor, this);
		}
	}

	public void setMediaExecutor(HTML5VideoMediaExecutor mediaExecutor) {
		this.mediaExecutor = mediaExecutor;
	}

	public void setMediaWrapper(HTML5VideoMediaWrapper mediaWrapper) {
		this.mediaWrapper = mediaWrapper;
	}

}
