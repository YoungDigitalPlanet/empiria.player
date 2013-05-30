package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

public interface ColorfillInteractionView extends IsWidget {

	void createButton(ColorModel color);
	void selectButton(ColorModel color);
	void deselectButton(ColorModel color);
	void setButtonClickListener(ColorfillButtonClickListener listener);
	
	void setColor(Area area, ColorModel color);
	ColorModel getColor(Area area);
	void setColors(Map<Area, ColorModel> colors);
	void setAreaClickListener(ColorfillAreaClickListener listener);
}
