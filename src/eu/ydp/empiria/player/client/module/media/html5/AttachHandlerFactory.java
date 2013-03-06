package eu.ydp.empiria.player.client.module.media.html5;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;

public class AttachHandlerFactory {

	@Inject
	private Provider<AttachHandlerImpl> attachHandlerProvider;

	public AttachHandlerImpl createAttachHandler(AbstractHTML5MediaExecutor mediaExecutor, AbstractHTML5MediaWrapper mediaWrapper) {
		AttachHandlerImpl attachHandlerImpl = attachHandlerProvider.get();
		attachHandlerImpl.setMediaExecutor(mediaExecutor);
		attachHandlerImpl.setMediaWrapper(mediaWrapper);
		mediaWrapper.getMediaObject().addAttachHandler(attachHandlerImpl);

		return attachHandlerImpl;
	}
}
