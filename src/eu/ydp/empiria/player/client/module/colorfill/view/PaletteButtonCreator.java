package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class PaletteButtonCreator {

	@Inject
	private Provider<PaletteButton> buttonProvider;
	
	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	
	public PaletteButton createButton(ColorModel color, Command command, String description){
		PaletteButton button = produceButton(color, description);
		applyEventHandler(command, button);
		return button;
	}

	private void applyEventHandler(Command command, IsWidget widget) {
		userInteractionHandlerFactory.applyUserClickHandler(command, widget);
	}

	private PaletteButton produceButton(ColorModel color, String description) {
		PaletteButton button = buttonProvider.get();
		button.setColor(color);
		button.setDescription(description);
		return button;
	}
}
