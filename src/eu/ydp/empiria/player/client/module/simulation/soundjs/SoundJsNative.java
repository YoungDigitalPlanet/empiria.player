package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.inject.Inject;

public class SoundJsNative {

	@Inject
	private SoundJsPlugin soundJsWrapperApi;

	public SoundJsNative() {
		nativesInit();
	}

	private native void nativesInit()/*-{
										var instance = this;
										$wnd.empiriaSoundJsBeginPlaying = function(src) {
										instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::play(Ljava/lang/String;)(src);
										}
										$wnd.empiriaSoundJsPreload = function(src) {
										instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::preload(Ljava/lang/String;)(src);
										}
										}-*/;

	private void play(String src) {
		soundJsWrapperApi.play(src);
	}

	private void preload(String src) {
		soundJsWrapperApi.preload(src);
	}

}
