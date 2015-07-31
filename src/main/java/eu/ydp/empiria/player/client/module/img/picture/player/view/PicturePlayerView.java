package eu.ydp.empiria.player.client.module.img.picture.player.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.PicturePlayerPresenter;

public interface PicturePlayerView extends IsWidget {
    void setImage(String url);

    void addFullscreenButton();

    void setPresenter(PicturePlayerPresenter presenter);
}
