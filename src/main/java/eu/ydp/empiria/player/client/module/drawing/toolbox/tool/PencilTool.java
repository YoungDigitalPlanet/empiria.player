package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.empiria.player.client.util.position.Point;

public class PencilTool implements Tool {

    public static final int LINE_WIDTH = 4;
    private final ColorModel color;
    private final DrawCanvas canvas;

    public PencilTool(ColorModel colorModel, DrawCanvas canvas) {
        this.color = colorModel;
        this.canvas = canvas;
    }

    @Override
    public void start(Point point) {
        canvas.drawPoint(point, color);
    }

    @Override
    public void move(Point start, Point end) {
        canvas.drawLine(start, end, color);
    }

    @Override
    public void setUp() {
        canvas.setLineWidth(LINE_WIDTH);
    }

}
