package eu.ydp.empiria.player.client.controller.messages;

import com.google.gwt.user.client.ui.PopupPanel;

public class ExtendedPopupPanel extends PopupPanel {

    public ExtendedPopupPanel(ExtendedPopupPanelDisplayEventListener l) {
        listener = l;
    }

    private ExtendedPopupPanelDisplayEventListener listener;

    @Override
    public void onAttach() {
        super.onAttach();
        listener.onMessageAttaching();
    }

    @Override
    public void hide() {
        super.hide();
        listener.onMessageHided();
    }
}
