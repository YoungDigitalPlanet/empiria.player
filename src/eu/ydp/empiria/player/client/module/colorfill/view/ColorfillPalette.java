package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public interface ColorfillPalette extends IsWidget {
	void createButton(ColorModel color, String string);
	void selectButton(ColorModel color);
	void deselectButton(ColorModel color);
	void setButtonClickListener(ColorfillButtonClickListener listener);
}
