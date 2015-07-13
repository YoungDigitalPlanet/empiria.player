package eu.ydp.empiria.player.client.module.drawing.toolbox.model;

import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public interface ToolboxModel {

    int getLineThickness();

    ColorModel getColorModel();

    ToolType getToolType();

}
