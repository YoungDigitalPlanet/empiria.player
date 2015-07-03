package eu.ydp.empiria.player.client.preloader.view;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ProgressBundle extends ClientBundle {

    @Source("preloader.gif")
    public ImageResource getProgressImage();
}
