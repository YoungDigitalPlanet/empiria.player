package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public interface ColorfillCanvas extends IsWidget {

	void setImage(Image image);
	
	void setColor(Area area, ColorModel color);
	ColorModel getColor(Area area);
	void setColors(Map<Area, ColorModel> colors);
	void setAreaClickListener(ColorfillAreaClickListener listener);
	void reset();
}
