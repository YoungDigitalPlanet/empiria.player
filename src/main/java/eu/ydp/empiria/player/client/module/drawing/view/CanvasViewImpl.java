package eu.ydp.empiria.player.client.module.drawing.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.util.geom.Size;

public class CanvasViewImpl extends Composite implements CanvasView {
    interface CanvasViewImplUiBinder extends UiBinder<Widget, CanvasViewImpl> {
    }

    private static CanvasViewImplUiBinder uiBinder = GWT.create(CanvasViewImplUiBinder.class);

    @UiField(provided = true)
    protected Canvas canvas;
    @UiField
    protected FlowPanel container;

    private final String defaultGlobalCompositeOperation;
    private final String destinationOutCompositeOperation = com.google.gwt.canvas.dom.client.Context2d.Composite.DESTINATION_OUT.getValue();

    private final String eraserColor = "#000000ff";
    private final CanvasDragHandlers canvasDragHandlers;
    private int currentLineWidth = 4;

    @Inject
    public CanvasViewImpl(CanvasDragHandlers canvasDragHandlers) {
        this.canvasDragHandlers = canvasDragHandlers;
        canvas = Canvas.createIfSupported();
        defaultGlobalCompositeOperation = canvas.getContext2d().getGlobalCompositeOperation();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void drawPoint(Point point, ColorModel color) {
        drawLine(point, new Point(point.getX(), point.getY() + 1), color);
    }

    private Context2d getContext(String globalCompositeOperation) {
        Context2d context2d = canvas.getContext2d();
        context2d.setLineWidth(currentLineWidth);
        context2d.setGlobalCompositeOperation(globalCompositeOperation);
        return context2d;
    }

    @Override
    public void drawLine(Point startPoint, Point endPoint, ColorModel color) {
        Context2d context2d = getContext(defaultGlobalCompositeOperation);
        context2d.beginPath();
        context2d.setStrokeStyle("#" + color.toStringRgb());
        context2d.moveTo(startPoint.getX(), startPoint.getY());
        context2d.lineTo(endPoint.getX(), endPoint.getY());
        context2d.stroke();
    }

    @Override
    public void erasePoint(Point point) {
        eraseLine(point, new Point(point.getX(), point.getY() + 1));
    }

    @Override
    public void eraseLine(Point startPoint, Point endPoint) {
        Context2d context2d = getContext(destinationOutCompositeOperation);
        context2d.beginPath();
        context2d.moveTo(startPoint.getX(), startPoint.getY());
        context2d.lineTo(endPoint.getX(), endPoint.getY());
        context2d.setStrokeStyle(eraserColor);
        context2d.stroke();
    }

    @Override
    public void clear() {
        getContext(defaultGlobalCompositeOperation).clearRect(0, 0, canvas.getOffsetWidth(), canvas.getOffsetHeight());
    }

    @Override
    public void setBackground(String url) {
        Style style = container.getElement().getStyle();
        style.setBackgroundImage("url(" + url + ")");
    }

    @Override
    public void setSize(Size size) {
        canvas.setCoordinateSpaceHeight(size.getHeight());
        canvas.setCoordinateSpaceWidth(size.getWidth());
        applySize(canvas, size);
        applySize(container, size);
    }

    private void applySize(UIObject uiObject, Size size) {
        uiObject.setWidth(size.getWidth() + "px");
        uiObject.setHeight(size.getHeight() + "px");
    }

    @Override
    public void initializeInteractionHandlers(CanvasPresenter canvasPresenter) {
        canvasDragHandlers.addHandlersToView(canvasPresenter, canvas);
    }

    @Override
    public void setLineWidth(int lineWidth) {
        this.currentLineWidth = lineWidth;
    }
}
