package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.IsWidget;


public interface Video extends IsWidget {

	public void setSrc(String src);
	public void addSrc(String src,String type);
	public void setWidth(int width);
	public void setHeight(int height);
	public void setPoster(String url);
	public void setShowNativeControls(boolean show);
	public MediaBase getMedia();
}
