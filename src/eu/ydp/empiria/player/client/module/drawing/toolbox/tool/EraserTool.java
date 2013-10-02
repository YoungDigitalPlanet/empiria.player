package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.util.position.Point;

public class EraserTool implements Tool {

	private static final int LINE_WIDTH = 10;
	private final DrawCanvas canvas;

	public EraserTool(DrawCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void start(Point point) {
		canvas.setLineWidth(LINE_WIDTH);
		canvas.erasePoint(point);
	}

	@Override
	public void move(Point start, Point end) {
		canvas.setLineWidth(LINE_WIDTH);
		canvas.eraseLine(start, end);
	}

}
