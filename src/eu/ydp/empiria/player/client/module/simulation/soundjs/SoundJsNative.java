package eu.ydp.empiria.player.client.module.simulation.soundjs;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

public class SoundJsNative {

	private SoundApiForJs api;
	private final Map<String, JavaScriptObject> soundInstances = new HashMap<>();

	public SoundJsNative() {
		nativesInit();
	}

	public void setApiForJs(SoundApiForJs apiForJs) {
		this.api = apiForJs;
	}

	private native void nativesInit()/*-{
										var instance = this;
										$wnd.empiriaSoundJsBeginPlaying = function (src) {
										instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::play(Ljava/lang/String;)(src);
										}
										$wnd.empiriaSoundJsBeginPlayingLooped = function(src) {
											instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::playLooped(Ljava/lang/String;)(src);
										}
										$wnd.empiriaSoundJsInit = function (soundInstance, src) {
										instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::preload(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)(soundInstance, src);
										}
										$wnd.empiriaSoundJsStop = function (src) {
										instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::stop(Ljava/lang/String;)(src);
										}
										$wnd.empiriaSoundJsPause = function (src) {
										instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::pause(Ljava/lang/String;)(src);
										}
										$wnd.empiriaSoundJsResume = function (src) {
										instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::resume(Ljava/lang/String;)(src);
										}
										}-*/;

	private native void onComplete(JavaScriptObject soundInstance)/*-{
																	if (typeof soundInstance.onComplete == 'function') {
																	soundInstance.onComplete(soundInstance);
																	}
																	}-*/;

	public void onComplete(String str) {
		onComplete(soundInstances.get(str));
	}

	private void play(String src) {
		api.play(src);
	}

	private void playLooped(String src) {
		api.playLooped(src);
	}

	private void preload(JavaScriptObject soundInstance, String src) {
		soundInstances.put(src, soundInstance);
		api.preload(src);
	}

	private void stop(String src) {
		api.stop(src);
	}

	private void pause(String src) {
		api.pause(src);
	}

	private void resume(String src) {
		api.resume(src);
	}
}
