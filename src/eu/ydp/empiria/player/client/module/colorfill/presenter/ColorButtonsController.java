package eu.ydp.empiria.player.client.module.colorfill.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ColorButtonsController {

	private final ColorfillInteractionView interactionView;
	private ColorModel currentSelectedButtonColor;

	@Inject
	public ColorButtonsController(@ModuleScoped ColorfillInteractionView interactionView) {
		this.interactionView = interactionView;
	}

	public ColorModel getCurrentSelectedButtonColor() {
		return currentSelectedButtonColor;
	}

	public void colorButtonClicked(ColorModel color){
		if(currentSelectedButtonColor == null){
			currentSelectedButtonColor = color;
			interactionView.selectButton(color);
		} else if(currentSelectedButtonColor.equals(color)){
			currentSelectedButtonColor = null;
			interactionView.deselectButton(color);
		} else {
			interactionView.deselectButton(currentSelectedButtonColor);
			interactionView.selectButton(color);
			currentSelectedButtonColor = color;
		}
	}
}
