package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.img.explorable.view.ImageProperties;

public interface ExplorableImgWindow extends IsWidget {

    void init(String imageUrl, ImageProperties styles, String title);

    void zoomIn();

    void zoomOut();
}
