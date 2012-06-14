package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.IsWidget;


public interface Audio extends IsWidget {

  /** create xml with embeded video */
  public void setSource(String src);
  public void addSource(String src,String type);
  public void setShowNativeControls(boolean show);

  public MediaBase getMedia();
}
