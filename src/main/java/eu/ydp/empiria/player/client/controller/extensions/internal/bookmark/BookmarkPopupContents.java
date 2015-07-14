package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BookmarkPopupContents extends Composite implements IBookmarkPopupContentsView {

    private static BookmarkPopupPanelUiBinder uiBinder = GWT.create(BookmarkPopupPanelUiBinder.class);

    interface BookmarkPopupPanelUiBinder extends UiBinder<Widget, BookmarkPopupContents> {
    }

    IBookmarkPopupContentsPresenter presenter;
    private HandlerRegistration previewHandlerRegistration;

    @UiField
    TextBox titleTextBox;

    @UiField
    FlowPanel innerInnerPanel;

    public BookmarkPopupContents() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setFocus() {
        titleTextBox.setFocus(true);
        titleTextBox.setSelectionRange(0, titleTextBox.getText().length());
    }

    @Override
    public void setPresenter(IBookmarkPopupContentsPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("okButton")
    public void okClickHandler(ClickEvent event) {
        presenter.applyBookmark();
    }

    @UiHandler("deleteButton")
    public void deleteClickHandler(ClickEvent event) {
        presenter.deleteBookmark();
    }

    @UiHandler("closeButton")
    public void closeClickHandler(ClickEvent event) {
        presenter.discardChanges();
    }

    @UiHandler("titleTextBox")
    public void titleKeyDownHandler(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            presenter.applyBookmark();
        } else if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
            presenter.discardChanges();
        }
    }

    @UiHandler("titleTextBox")
    public void focusHandler(FocusEvent event) {
        previewHandlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(NativePreviewEvent preview) {
                NativeEvent event = preview.getNativeEvent();
                Element elt = event.getEventTarget().cast();
                String eventType = event.getType();
                boolean touched = eventType.equals("mousedown") || eventType.equals("touchstart");
                if (touched && !innerInnerPanel.getElement().isOrHasChild(elt)) {
                    presenter.applyBookmark();
                }
            }
        });

    }

    @Override
    public void setBookmarkTitle(String title) {
        titleTextBox.setText(title);
    }

    @Override
    public String getBookmarkTitle() {
        return titleTextBox.getText();
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        if (previewHandlerRegistration != null) {
            previewHandlerRegistration.removeHandler();
        }
    }

}
