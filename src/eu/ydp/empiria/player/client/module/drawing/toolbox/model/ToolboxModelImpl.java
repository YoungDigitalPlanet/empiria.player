package eu.ydp.empiria.player.client.module.drawing.toolbox.model;

import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public class ToolboxModelImpl implements ToolboxModel {
	private ColorModel colorModel;
	private ToolType toolType;

	@Override
	public int getLineThickness() {
		return 0;
	}

	@Override
	public ColorModel getColorModel() {
		return colorModel;
	}

	public void setColorModel(ColorModel colorModel) {
		this.colorModel = colorModel;
	}

	@Override
	public ToolType getToolType() {
		return toolType;
	}

	public void setToolType(ToolType toolType) {
		this.toolType = toolType;
	}
}
