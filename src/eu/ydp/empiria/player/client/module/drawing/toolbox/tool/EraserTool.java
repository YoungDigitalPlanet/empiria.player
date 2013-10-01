package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import com.google.common.base.Strings;
import com.google.gwt.user.client.Window;

import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

public class EraserTool implements Tool {

	private static final Logger LOGGER = new Logger();
	private static final int LINE_WIDTH = 10;
	private final DrawCanvas canvas;

	public EraserTool(DrawCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void start(Point point) {
		canvas.setLineWidth(getLineWidth());
		canvas.erasePoint(point);
	}

	@Override
	public void move(Point start, Point end) {
		canvas.setLineWidth(getLineWidth());
		canvas.eraseLine(start, end);
	}

	public static int getLineWidth() {
		String parameter = Window.Location.getParameter("lineWidth");
		
		int lineWidth = LINE_WIDTH;
		if(!Strings.isNullOrEmpty(parameter)) {
			lineWidth = Integer.valueOf(parameter); 
		}
		
		LOGGER.info("Using line width: "+lineWidth);
		return lineWidth;
	}

}
