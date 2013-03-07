package eu.ydp.empiria.player.client.module.media.html5;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;

public class AttachHandlerFactory {

	@Inject
	private Provider<AttachHandlerImpl> attachHandlerProvider;

	public AttachHandlerImpl createAttachHandler(HTML5VideoMediaExecutor mediaExecutor, HTML5VideoMediaWrapper mediaWrapper) {
		AttachHandlerImpl attachHandlerImpl = attachHandlerProvider.get();
		attachHandlerImpl.setMediaExecutor(mediaExecutor);
		attachHandlerImpl.setMediaWrapper(mediaWrapper);
		mediaWrapper.getMediaObject().addAttachHandler(attachHandlerImpl);

		return attachHandlerImpl;
	}
}
