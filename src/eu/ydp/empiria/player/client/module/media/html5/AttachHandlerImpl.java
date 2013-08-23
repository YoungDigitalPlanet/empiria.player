package eu.ydp.empiria.player.client.module.media.html5;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.isUserAgent;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.module.media.html5.reattachhack.HTML5VideoReattachHack;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class AttachHandlerImpl implements Handler {

	private HTML5VideoMediaExecutor mediaExecutor;
	private HTML5VideoMediaWrapper mediaWrapper;
	private boolean firstTimeInitialization = true;

	@Inject
	protected EventsBus eventsBus;

	@Inject
	Provider<HTML5VideoReattachHack> html5VideoReattachHackProvider;

	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if (firstTimeInitialization) {
			firstTimeInitialization = false;
			return;
		}

		if (event.isAttached()) {
			if( isUserAgent(MobileUserAgent.SAFARI) ){
				HTML5VideoReattachHack html5VideoReattachHack = html5VideoReattachHackProvider.get();
				html5VideoReattachHack.reAttachVideo(mediaWrapper, mediaExecutor, this);
			}
		}
	}

	public void setMediaExecutor(HTML5VideoMediaExecutor mediaExecutor) {
		this.mediaExecutor = mediaExecutor;
	}

	public void setMediaWrapper(HTML5VideoMediaWrapper mediaWrapper) {
		this.mediaWrapper = mediaWrapper;
	}

}
