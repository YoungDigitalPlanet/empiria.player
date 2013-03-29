package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.dom.client.NativeEvent;

import eu.ydp.gwtutil.client.event.factory.Command;

public class ChoiceButtonMouseInteractionCommand implements Command {

	private final boolean over;
	private final SelectionChoiceButton button;
	public ChoiceButtonMouseInteractionCommand(SelectionChoiceButton button, boolean over) {
		this.button = button;
		this.over = over;
	}
	@Override
	public void execute(NativeEvent event) {
		button.setMouseOver(over);
	}

}
