package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.IsWidget;


public interface VideoImpl extends IsWidget {

	public void setSrc(String src);
	public void addSrc(String src,String type);
	public void setWidth(int width);
	public void setHeight(int height);
	public void setPoster(String url);
	public void setShowNativeControls(boolean show);
	public Video getMedia();
}
