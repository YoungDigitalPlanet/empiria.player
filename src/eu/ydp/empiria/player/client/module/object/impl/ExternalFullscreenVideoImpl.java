package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class ExternalFullscreenVideoImpl implements Video {

	private Image poster = new Image();
	
	public ExternalFullscreenVideoImpl() {
	}
	
	@Override
	public void addSrc(String src, String type) {
	}

	@Override
	public void setShowNativeControls(boolean show) {
	}

	@Override
	public void setEventBusSourceObject(MediaWrapper<?> object) {
	}

	@Override
	public MediaBase getMedia() {
		return null;
	}

	@Override
	public Widget asWidget() {
		return poster;
	}

	@Override
	public void setWidth(int width) {
		poster.setWidth(width + "px");
	}

	@Override
	public void setHeight(int height) {
		poster.setHeight(height + "px");
	}

	@Override
	public void setPoster(String url) {
		poster.setUrl(url);
	}

}
