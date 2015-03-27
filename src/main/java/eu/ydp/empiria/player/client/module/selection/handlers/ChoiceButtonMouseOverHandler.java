package eu.ydp.empiria.player.client.module.selection.handlers;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

public class ChoiceButtonMouseOverHandler implements MouseOverHandler {

	private final ChoiceButtonBase button;

	public ChoiceButtonMouseOverHandler(ChoiceButtonBase button) {
		this.button = button;
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		button.setMouseOver(true);
	}

}
