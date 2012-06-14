package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class EmbedAudioImpl extends Composite implements AudioImpl {
	HTML audio = null;

	public EmbedAudioImpl() {
		audio = new HTML();
		initWidget(audio);
	}

	public void setSource(String src) {
		audio.setHTML("<embed src='" + src + "' autostart='false' controller='true'></embed>");
	}

	@Override
	public void setShowNativeControls(boolean show) {
	}

	@Override
	public Audio getMedia() {
		return null;
	}
}
