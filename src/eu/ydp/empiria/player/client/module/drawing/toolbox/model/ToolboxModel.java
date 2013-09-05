package eu.ydp.empiria.player.client.module.drawing.toolbox.model;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;

public class ToolboxModel {
	private ColorModel colorModel;
	private ToolType toolType;

	public int getLineThickness() {
		return 0;
	}

	public ColorModel getColorModel() {
		return colorModel;
	}

	public void setColorModel(ColorModel colorModel) {
		this.colorModel = colorModel;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public void setToolType(ToolType toolType) {
		this.toolType = toolType;
	}
}
