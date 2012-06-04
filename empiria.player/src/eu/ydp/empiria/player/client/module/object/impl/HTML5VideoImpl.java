package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Composite;

public class HTML5VideoImpl extends Composite implements VideoImpl {

	protected Video video;

	public HTML5VideoImpl() {
		video = Video.createIfSupported();
		video.setControls(true);
		video.setPreload(MediaElement.PRELOAD_METADATA);
		initWidget(video);
	}

	public void setSrc(String src) {
		video.setSrc(src);
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
}
