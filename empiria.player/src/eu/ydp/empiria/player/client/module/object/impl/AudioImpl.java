package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.IsWidget;


public interface AudioImpl extends IsWidget {

  /** create xml with embeded video */
  public void setSource(String src);

  public void setShowNativeControls(boolean show);

  public Audio getMedia();
}
