package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.color.ColorModel;

public interface ToolboxView extends IsWidget {
	 void showPalette();
	 void hidePalette();
	 void selectPencil();
	 void selectEraser();
	 void unselectTools();
	 void setPaletteColor( ColorModel colorModel );
	 void setPalette(ColorModel colorModel);
}
