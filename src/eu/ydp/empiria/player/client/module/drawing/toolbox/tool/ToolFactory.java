package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModel;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ToolFactory {
	
	@Inject
	@ModuleScoped
	private DrawCanvas canvas;
	
	public Tool createTool(ToolboxModel model) {
		ToolType toolType = model.getToolType();
		ColorModel colorModel = model.getColorModel();
		
		switch (toolType) {
		case ERASER:
			return new EraserTool(canvas);
		case PENCIL:
			return new PencilTool(colorModel, canvas);
		default:
			throw new UnknownToolTypeException(toolType + " is unknown tool type.");
		}
	}
}
