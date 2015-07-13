package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import eu.ydp.gwtutil.client.geom.Rectangle;

public interface IBookmarkPopupView {

    void setPresenter(IBookmarkPopupPresenter presenter);

    void init();

    void show(Rectangle area);

    void setBookmarkTitle(String title);

    String getBookmarkTitle();
}
