package eu.ydp.empiria.player.client.module.object.impl;


import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class EmbedAudioImpl extends Composite implements Audio {
	HTML audio = null;

	public EmbedAudioImpl() {
		audio = new HTML();
		initWidget(audio);
	}

	public void setSrc(String src) {
		audio.setHTML("<embed src='" + src + "' autostart='false' controller='true'></embed>");
	}

	@Override
	public void setShowNativeControls(boolean show) {
	}

	@Override
	public MediaBase getMedia() {
		return null;
	}

	@Override
	public void addSrc(String src, String type) {
		setSrc(src);
	}

	@Override
	public void setEventBusSourceObject(MediaWrapper<?> object) {

	}
}
