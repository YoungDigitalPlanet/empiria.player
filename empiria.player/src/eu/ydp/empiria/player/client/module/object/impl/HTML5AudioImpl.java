package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;

public class HTML5AudioImpl extends Composite implements Audio {
	com.google.gwt.media.client.Audio audio = null;

	public HTML5AudioImpl() {
		audio = com.google.gwt.media.client.Audio.createIfSupported();
		initWidget(audio);
	}

	public void setSrc(String src) {
		audio.setSrc(src);
	}

	@Override
	public void setShowNativeControls(boolean show) {
		audio.setControls(show);
	}

	@Override
	public MediaBase getMedia() {
		return audio;
	}

	@Override
	public void addSrc(String src, String type) {
		audio.addSource(src, type);
	}
}
