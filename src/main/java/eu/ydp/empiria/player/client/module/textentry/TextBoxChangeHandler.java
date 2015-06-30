package eu.ydp.empiria.player.client.module.textentry;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.TextBox;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public class TextBoxChangeHandler implements BlurHandler, DropHandler {

    private PresenterHandler presenterHandler;

    public void bind(DroppableObject<TextBox> listBox, PresenterHandler presenterHandler) {
        if (presenterHandler != null) {
            this.presenterHandler = presenterHandler;
            listBox.getOriginalWidget().addBlurHandler(this);
            listBox.addDropHandler(this);
        }
    }

    @Override
    public void onDrop(DropEvent event) {
        BlurEvent noopBlurEvent = new BlurEvent() {
        };
        callBlurHandler(noopBlurEvent);
    }

    @Override
    public void onBlur(BlurEvent event) {
        callBlurHandler(event);
    }

    private void callBlurHandler(BlurEvent event) {
        presenterHandler.onBlur(event);
    }

}
