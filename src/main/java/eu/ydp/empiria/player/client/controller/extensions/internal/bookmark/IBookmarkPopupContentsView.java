package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

public interface IBookmarkPopupContentsView {

    void setPresenter(IBookmarkPopupContentsPresenter presenter);

    void setBookmarkTitle(String title);

    String getBookmarkTitle();
}
