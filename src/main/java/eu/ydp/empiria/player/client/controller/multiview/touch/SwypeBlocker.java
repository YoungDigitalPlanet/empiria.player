package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxOpenCloseListener;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;

public class SwypeBlocker {

    private final TouchController touchController;

    @Inject
    public SwypeBlocker(TouchController touchController) {
        this.touchController = touchController;
    }

    public void addBlockOnOpenCloseHandler(IsExListBox exListBox) {
        exListBox.addOpenCloseListener(new OpenCloseListener());
    }

    class OpenCloseListener implements ExListBoxOpenCloseListener {

        @Override
        public void onClose(CloseEvent<PopupPanel> event) {
            touchController.setSwypeLock(false);

        }

        @Override
        public void onOpen() {
            touchController.setSwypeLock(true);

        }

    }

}
