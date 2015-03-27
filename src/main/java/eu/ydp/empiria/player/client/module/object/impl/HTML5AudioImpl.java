package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class HTML5AudioImpl extends FlowPanel implements Audio {
	eu.ydp.empiria.player.client.media.Audio audio = null;

	public HTML5AudioImpl() {
		audio = new eu.ydp.empiria.player.client.media.Audio();
		add(audio);
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

	@Override
	public void setEventBusSourceObject(MediaWrapper<?> object) {

	}
}
