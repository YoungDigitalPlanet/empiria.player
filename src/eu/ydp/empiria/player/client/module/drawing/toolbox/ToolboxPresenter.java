package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.color.ColorModel;

public class ToolboxPresenter {
	@Inject private ToolboxView view;


	 public void init(){}
	 public void colorClicked( ColorModel colorModel){}
	 public void paletteClicked(){}
	 public void pencilClicked(){}
	 public void eraserClicked(){}
	 public void clearAllClicked(){}

	 public ToolboxView getView() {
		return view;
	}
}
