package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;

public interface PaletteButton extends IsWidget {

	void setColor(ColorModel color);
	void select();
	void deselect();
	void setDescription(String description);
}
