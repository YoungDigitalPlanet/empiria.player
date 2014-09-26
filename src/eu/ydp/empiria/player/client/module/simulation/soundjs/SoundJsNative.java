package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.HashMap;
import java.util.Map;

public class SoundJsNative {

	private SoundJsPlugin soundJsPlugin;
	private final Map<String, JavaScriptObject> soundInstances = new HashMap<>();

	public SoundJsNative() {
		nativesInit();
	}

	public void setApiForJS(SoundJsPlugin soundJsPlugin) {
		this.soundJsPlugin = soundJsPlugin;
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
		soundJsPlugin.play(src);
	}

	private void preload(JavaScriptObject soundInstance, String src) {
		soundInstances.put(src, soundInstance);
		soundJsPlugin.preload(src);
	}

	private void stop(String src) {
		soundJsPlugin.stop(src);
	}
}
