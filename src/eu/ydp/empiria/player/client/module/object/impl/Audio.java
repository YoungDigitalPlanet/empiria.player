package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.IsWidget;


public interface Audio extends IsWidget {

  /** create xml with embeded video */
  public void setSrc(String src);
  public void addSrc(String src,String type);
  public void setShowNativeControls(boolean show);

  public MediaBase getMedia();
}
