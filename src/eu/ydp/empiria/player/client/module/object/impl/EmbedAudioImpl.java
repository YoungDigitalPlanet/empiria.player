package eu.ydp.empiria.player.client.module.object.impl;


import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class EmbedAudioImpl extends Composite implements Audio {
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
	public MediaBase getMedia() {
		return null;
	}

	@Override
	public void addSource(String src, String type) {
		setSource(src);
	}
}
