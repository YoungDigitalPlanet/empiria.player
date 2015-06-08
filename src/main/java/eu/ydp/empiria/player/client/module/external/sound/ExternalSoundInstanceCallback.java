package eu.ydp.empiria.player.client.module.external.sound;

import com.google.gwt.core.client.js.JsType;

@JsType
public interface ExternalSoundInstanceCallback {
	void onSoundCreated(ExternalSoundInstance soundInstance, String baseSrc);
}
