package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Composite;

public class HTML5AudioImpl extends Composite implements AudioImpl {
	Audio audio = null;

	public HTML5AudioImpl() {
		audio = Audio.createIfSupported();
		initWidget(audio);
	}

	public void setSource(String src) {
		audio.setSrc(src);
	}

	@Override
	public void setShowNativeControls(boolean show) {
		audio.setControls(show);
	}

	@Override
	public Audio getMedia() {
		return audio;
	}
}
