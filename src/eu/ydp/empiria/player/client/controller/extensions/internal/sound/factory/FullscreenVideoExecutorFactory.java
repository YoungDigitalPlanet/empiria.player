package eu.ydp.empiria.player.client.controller.extensions.internal.sound.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoExecutor;
import eu.ydp.empiria.player.client.module.media.external.FullscreenVideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.ExternalFullscreenVideoImpl;

public class FullscreenVideoExecutorFactory {

	@Inject private Provider<FullscreenVideoExecutor> executorProvider;
	@Inject private Provider<FullscreenVideoMediaWrapper> mediaWrapperProvider;
	
	public FullscreenVideoExecutor create(){
		FullscreenVideoExecutor exec = executorProvider.get();
		FullscreenVideoMediaWrapper mw = mediaWrapperProvider.get();
		mw.setMediaObject(new ExternalFullscreenVideoImpl());
		exec.setMediaWrapper(mw);
		return exec;
	}
	
}
