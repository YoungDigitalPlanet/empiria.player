package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.ExternalInteractionModuleFactory;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.external.common.ExternalInteractionPaths;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalSoundInstanceCreator {

	private ExternalInteractionPaths paths;
	private MediaWrapperCreator mediaWrapperCreator;
	private ExternalInteractionModuleFactory moduleFactory;

	@Inject
	public ExternalSoundInstanceCreator(@ModuleScoped ExternalInteractionPaths paths, MediaWrapperCreator mediaWrapperCreator,
			ExternalInteractionModuleFactory moduleFactory) {
		this.paths = paths;
		this.mediaWrapperCreator = mediaWrapperCreator;
		this.moduleFactory = moduleFactory;
	}

	public void createSound(final String audioName, final ExternalSoundInstanceCallback callback) {
		String audioPath = paths.getExternalFilePath(audioName);
		mediaWrapperCreator.createMediaWrapper(audioPath, createCallbackReceiver(audioName, callback));
	}

	private CallbackReceiver<MediaWrapper<Widget>> createCallbackReceiver(final String audioName, final ExternalSoundInstanceCallback callback) {
		return new CallbackReceiver<MediaWrapper<Widget>>() {
			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> audioWrapper) {
				ExternalSoundInstance soundInstance = moduleFactory.getExternalSoundInstance(audioWrapper);
				callback.onSoundCreated(soundInstance, audioName);
			}
		};
	}
}
