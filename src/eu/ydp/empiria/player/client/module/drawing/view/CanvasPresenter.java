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
	private boolean mouseOutWhileActive = false;

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
		if(cameBackToScreen()) {
			overridePreviousPosition(point);
		}
		
		if(isActive()) {
			moveTool(point);
		}
	}

	private void overridePreviousPosition(Point point) {
		previousPoint = point;
		mouseOutWhileActive = false;
	}

	private boolean cameBackToScreen() {
		return mouseOutWhileActive && isActive();
	}

	private void moveTool(Point point) {
		currentTool.move(previousPoint, point);
		previousPoint = point;
	}

	private boolean isActive() {
		return previousPoint != null;
	}

	public void mouseUp() {
		previousPoint = null;
	}

	public void mouseOut() {
		if(isActive()) { 
			this.mouseOutWhileActive = true;
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
