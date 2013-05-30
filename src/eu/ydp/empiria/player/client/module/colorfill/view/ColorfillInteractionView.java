package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.util.position.Point;

public interface ColorfillInteractionView extends IsWidget {

	void createButton(ColorModel color);
	void selectButton(ColorModel color);
	void deselectButton(ColorModel color);
	void setButtonClickListener(ColorfillButtonClickListener listener);
	
	void setColor(Point point, ColorModel color);
	ColorModel getColor(Point point);
	void setColors(Map<Point, ColorModel> colors);
	void setAreaClickListener(ColorfillAreaClickListener listener);
}
