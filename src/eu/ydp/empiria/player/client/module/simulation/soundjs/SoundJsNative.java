package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

public class SoundJsNative {

	@Inject
	private SoundJsPlugin soundJsWrapperApi;

	private final Map<String, JavaScriptObject> soundInstances = new HashMap<>();

	public SoundJsNative() {
		nativesInit();
	}

	private native void nativesInit()/*-{
        var instance = this;
        $wnd.empiriaSoundJsBeginPlaying = function (src) {
            instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::play(Ljava/lang/String;)(src);
        }
        $wnd.empiriaSoundJsInit = function (soundInstance, src) {
            instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::preload(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)(soundInstance, src);
        }
        $wnd.empiriaSoundJsStop = function (src) {
            instance.@eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative::stop(Ljava/lang/String;)(src);
        }
    }-*/;

	private void play(String src) {
		soundJsWrapperApi.play(src);
	}

	private void preload(JavaScriptObject soundInstance, String src) {
		soundInstances.put(src, soundInstance);
		soundJsWrapperApi.preload(src);
	}

	private void stop(String src) {
		soundJsWrapperApi.stop(src);
	}

}
