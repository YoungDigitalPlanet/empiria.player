package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.gwt.core.client.js.JsType;

@JsType
public interface ExternalSoundInstanceCallback {
	void onSoundCreated(ExternalSoundInstance soundInstance, String baseSrc);
}
