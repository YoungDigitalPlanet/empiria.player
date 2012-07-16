package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.FlowPanel;

public class HTML5VideoImpl extends FlowPanel implements Video {

	protected com.google.gwt.media.client.Video video;
	public HTML5VideoImpl() {
		video = com.google.gwt.media.client.Video.createIfSupported();
		video.setPreload(MediaElement.PRELOAD_METADATA);
		add(video);

	}

	public void setSrc(String src) {
		video.setSrc(src);
	}

	@Override
	public void addSrc(String src, String type) {
		video.addSource(src, type);
	}
	@Override
	public void setWidth(int width) {
		video.setWidth(width+"px");

	}

	@Override
	public void setHeight(int height) {
		video.setHeight(height+"px");
	}

	@Override
	public void setPoster(String url) {
		//TODO na ktoryms ios-ie jest blad z posterem sprawdzic i zrobic implementacje dla niego
		video.setPoster(url);
	}

	@Override
	public void setShowNativeControls(boolean show) {
		video.setControls(show);
	}
	@Override
	public MediaBase getMedia() {
		return video;
	}
}
