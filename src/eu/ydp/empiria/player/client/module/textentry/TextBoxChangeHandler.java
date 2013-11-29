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

	private class CustomBlurEvent extends BlurEvent { // NOPMD

	}

	public void bind(DroppableObject<TextBox> listBox, PresenterHandler presenterHandler) {
		if (presenterHandler != null) {
			this.presenterHandler = presenterHandler;
			listBox.getOriginalWidget().addBlurHandler(this);
			listBox.addDropHandler(this);
		}
	}


	@Override
	public void onDrop(DropEvent event) {
		onBlur(new CustomBlurEvent());
	}

	@Override
	public void onBlur(BlurEvent event) {
		presenterHandler.onBlur(event);
	}

}
