package eu.ydp.empiria.player.client.module.drawing.view;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.geom.Size;

public class CanvasPresenter {

	private final CanvasView canvasView;
	private Tool currentTool;
	private Point previousPoint;
	private boolean mouseOut = false;

	@Inject
	public CanvasPresenter(@ModuleScoped CanvasView canvasView) {
		this.canvasView = canvasView;
		this.canvasView.initializeInteractionHandlers(this);
	}

	public void mouseDown(Point point) {
		currentTool.start(point);
		this.previousPoint = point;
	}

	public void mouseMove(Point point) {
		if(mouseOut) {
			previousPoint = point;
			mouseOut = false;
		}
		
		if(previousPoint != null) {
			currentTool.move(previousPoint, point);
			previousPoint = point;
		}
	}

	public void mouseUp() {
		previousPoint = null;
	}

	public void mouseOut() {
		if(previousPoint != null) { 
			this.mouseOut = true;
		}
	}

	public void setTool(Tool tool) {
		this.currentTool = tool;
	}

	public void setImage(String url, Size size) {
		canvasView.setSize(size);
		canvasView.setBackground(url);
	}

	public CanvasView getView() {
		return canvasView;
	}
}
