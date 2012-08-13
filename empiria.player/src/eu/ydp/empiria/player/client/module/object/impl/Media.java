package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.IsWidget;

public interface Media extends IsWidget {
	  public void addSrc(String src,String type);
	  public void setShowNativeControls(boolean show);
	  public void setEventBusSourceObject(Object object);
	  public MediaBase getMedia();
}
