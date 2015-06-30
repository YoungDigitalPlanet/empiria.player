package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class BookmarkPopup implements IBookmarkPopupView, IBookmarkPopupContentsPresenter {

    BookmarkPopupContents contents = new BookmarkPopupContents();
    PopupPanel popup = new PopupPanel(true, true);

    @Inject
    StyleNameConstants styleNameConstants;
    @Inject
    EventsBus eventBus;

    private IBookmarkPopupPresenter presenter;

    @Override
    public void init() {
        popup.setStylePrimaryName(styleNameConstants.QP_BOOKMARK_POPUP());
        popup.setGlassEnabled(true);
        popup.setGlassStyleName(styleNameConstants.QP_BOOKMARK_POPUP_GLASS());
        popup.add(contents);
        popup.addCloseHandler(new CloseHandler<PopupPanel>() {

            @Override
            public void onClose(CloseEvent<PopupPanel> event) {
                if (event.isAutoClosed()) {
                    presenter.applyBookmark();
                }
            }
        });
        popup.addDomHandler(new TouchStartHandler() {

            @Override
            public void onTouchStart(TouchStartEvent event) {
                eventBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
            }
        }, TouchStartEvent.getType());
    }

    @Override
    public void setPresenter(IBookmarkPopupPresenter presenter) {
        this.presenter = presenter;
        contents.setPresenter(this);
    }

    @Override
    public void setBookmarkTitle(String title) {
        contents.setBookmarkTitle(title);
    }

    @Override
    public String getBookmarkTitle() {
        return contents.getBookmarkTitle();
    }

    @Override
    public void show(Rectangle area) {
        popup.setPopupPosition(area.getLeft(), area.getTop());
        contents.setSize(area.getWidth() + "px", area.getHeight() + "px");
        popup.show();
        contents.setFocus();
    }

    @Override
    public void applyBookmark() {
        popup.hide();
        presenter.applyBookmark();
    }

    @Override
    public void deleteBookmark() {
        popup.hide();
        presenter.deleteBookmark();
    }

    @Override
    public void discardChanges() {
        popup.hide(false);
        presenter.discardBookmarkChanges();
    }
}
