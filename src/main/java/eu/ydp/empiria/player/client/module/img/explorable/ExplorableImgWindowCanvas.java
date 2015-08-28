package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.components.PanelWithScrollbars;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.module.img.events.handlers.ITouchHandlerOnImageInitializer;
import eu.ydp.empiria.player.client.module.img.events.handlers.TouchHandlerOnImageProvider;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.module.img.explorable.view.ImageProperties;
import eu.ydp.empiria.player.client.util.position.Point;

public class ExplorableImgWindowCanvas extends AbstractExplorableImgWindowBase implements CanvasMoveEvents {

    private static ExplorableImgWindowCanvasUiBinder uiBinder = GWT.create(ExplorableImgWindowCanvasUiBinder.class);

    interface ExplorableImgWindowCanvasUiBinder extends UiBinder<Widget, ExplorableImgWindowCanvas> {
    }

    @UiField
    protected FlowPanel imagePanel;
    @UiField(provided = true)
    protected Canvas canvas;
    @UiField
    protected PanelWithScrollbars scrollbarsPanel;

    private final Context2d context2d;
    private static final int REDRAW_INTERVAL_MIN = 50;
    private double imgX = 0;
    private double imgY = 0;

    private double prevX, prevY;
    private boolean moving = false;
    private double prevDistance = -1;
    private long lastRedrawTime = -1;
    private boolean imageLoaded = false;

    private Image tempImage;

    private final TouchController touchController;

    private final ITouchHandlerOnImageInitializer touchHandlerInitializer;

    @Inject
    public ExplorableImgWindowCanvas(TouchController touchController, TouchHandlerOnImageProvider touchHandlerOnImageProvider) {
        canvas = Canvas.createIfSupported();
        initWidget(uiBinder.createAndBindUi(this));
        context2d = canvas.getContext2d();
        this.touchController = touchController;
        touchHandlerInitializer = touchHandlerOnImageProvider.get();
    }

    @Override
    public void init(String imageUrl, ImageProperties properties, String title) {
        setWindowWidth(properties.getWindowWidth());
        setWindowHeight(properties.getWindowHeight());
        setScale(properties.getScale());
        setScaleStep(properties.getScaleStep());
        setZoomMax(properties.getZoomMax());

        createAndInitializeTempImage(imageUrl);
        setUpImageCanvasProperties(title);

        scrollbarsPanel.setSize(getWindowWidth() + "px", getWindowHeight() + "px");
        FocusWidget focusCanvas = (FocusWidget) canvas.asWidget();
        addHandlersToCanvas(focusCanvas);
    }

    private void setUpImageCanvasProperties(String title) {
        canvas.setCoordinateSpaceWidth(getWindowWidth());
        canvas.setCoordinateSpaceHeight(getWindowHeight());
        canvas.setWidth(getWindowWidth() + "px");
        canvas.setHeight(getWindowHeight() + "px");
        canvas.setTitle(title);
    }

    private void createAndInitializeTempImage(String imageUrl) {
        tempImage = new Image(imageUrl);
        RootPanel.get().add(tempImage);
        // TODO: try to put img on a div with visibility:hidden
        // see
        // http://gwt-image-loader.googlecode.com/svn/trunk/src/com/reveregroup/gwt/imagepreloader/ImagePreloader.java
        if (!Navigator.getUserAgent().contains("MSIE")) {
            tempImage.setVisible(false);
        }

        tempImage.addLoadHandler(new LoadHandler() {

            @Override
            public void onLoad(LoadEvent event) {
                imageLoaded = true;

                setOriginalImageWidth(tempImage.getWidth());
                setOriginalImageHeight(tempImage.getHeight());

                findScaleMinAndOriginalAspectRatio();

                centerImage();
                redraw(false);

                RootPanel.get().remove(tempImage);
            }
        });

        tempImage.addErrorHandler(new ErrorHandler() {

            @Override
            public void onError(ErrorEvent event) {
                RootPanel.get().remove(tempImage);
            }
        });
    }

    private void addHandlersToCanvas(FocusWidget focusCanvas) {
        touchHandlerInitializer.addTouchOnImageStartHandler(createTouchOnImageStartHandler(), focusCanvas);
        touchHandlerInitializer.addTouchOnImageMoveHandler(createTouchOnImageMoveHandler(), focusCanvas);
        touchHandlerInitializer.addTouchOnImageEndHandler(createTouchOnImageEndHandler(), focusCanvas);

        addMouseDownHandler(focusCanvas);
        addMouseMoveHandler(focusCanvas);
        addMouseUpHandler(focusCanvas);
        addMouseOutHandler(focusCanvas);
    }

    private TouchOnImageMoveHandler createTouchOnImageMoveHandler() {
        return new TouchOnImageMoveHandler(this);
    }

    private TouchOnImageEndHandler createTouchOnImageEndHandler() {
        return new TouchOnImageEndHandler(this);
    }

    private TouchOnImageStartHandler createTouchOnImageStartHandler() {
        return new TouchOnImageStartHandler(this);
    }

    private void addMouseOutHandler(FocusWidget focusCanvas) {
        focusCanvas.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                onMoveEnd();
            }
        });
    }

    private void addMouseUpHandler(FocusWidget focusCanvas) {
        focusCanvas.addMouseUpHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp(MouseUpEvent event) {
                onMoveEnd();
            }
        });
    }

    private void addMouseMoveHandler(FocusWidget focusCanvas) {
        focusCanvas.addMouseMoveHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove(MouseMoveEvent event) {
                Point point = new Point(event.getClientX(), event.getClientY());
                onMoveMove(point);
            }
        });
    }

    private void addMouseDownHandler(FocusWidget focusCanvas) {
        focusCanvas.addMouseDownHandler(new MouseDownHandler() {

            @Override
            public void onMouseDown(MouseDownEvent event) {
                Point point = new Point(event.getClientX(), event.getClientY());
                onMoveStart(point);
            }
        });
    }

    @Override
    public void onMoveStart(Point point) {// NOPMD
        disableSwype();
        moving = true;
        prevX = point.getX();
        prevY = point.getY();
    }

    @Override
    public void onMoveScale(Point firstFinger, Point secondFinger) {// NOPMD
        double currDistance = firstFinger.distance(secondFinger);

        if (prevDistance != -1) {
            scaleBy(currDistance / prevDistance);
            redraw(true);
        }

        prevDistance = currDistance;
    }

    private void disableSwype() {
        touchController.setTouchReservation(true);
    }

    @Override
    public void onMoveMove(Point point) {
        int x = point.getX();
        int y = point.getY();

        disableSwype();

        if (moving) {

            double dx = x - prevX;
            double dy = y - prevY;

            double zoom = getZoom();

            imgX -= dx / zoom;
            imgY -= dy / zoom;

            redraw(true);

            prevX = x;
            prevY = y;

        }
    }

    @Override
    public void onMoveEnd() {
        moving = false;
        prevDistance = -1;
    }

    private void redraw(boolean showScrollbars) {

        checkImageCoords();

        double scaleNormalized = getScale();

        double sourceX = imgX;
        double sourceY = imgY;
        double sourceWidth = getOriginalImageWidth() / scaleNormalized;
        double sourceHeight = (getOriginalImageWidth() * getWindowHeight() / getWindowWidth()) / scaleNormalized;
        double destWidth = getWindowWidth();
        double destHeight = getWindowHeight();

        if (sourceX + sourceWidth > getOriginalImageWidth()) {
            sourceWidth = getOriginalImageWidth() - sourceX;
            double zoom = getZoom();
            destWidth = sourceWidth * zoom;
        }
        if (sourceY + sourceHeight > getOriginalImageHeight()) {
            sourceHeight = getOriginalImageHeight() - sourceY;
            double zoom = getZoom();
            destHeight = sourceHeight * zoom;
        }

        if (System.currentTimeMillis() - lastRedrawTime > REDRAW_INTERVAL_MIN) {
            if (imageLoaded) {
                double destX = 0;
                double destY = 0;
                clearContext();
                context2d.drawImage(ImageElement.as(tempImage.getElement()), sourceX, sourceY, sourceWidth, sourceHeight, destX, destY, destWidth, destHeight);
            }
            lastRedrawTime = System.currentTimeMillis();
            updateScrollbars(showScrollbars);
        }
    }

    private void clearContext() {
        context2d.clearRect(0, 0, getWindowWidth(), getWindowHeight());
    }

    private void updateScrollbars(boolean showScrollbars) {
        double posX = imgX * getZoom();
        double posY = imgY * getZoom();
        scrollbarsPanel.setHorizontalPosition(posX, getWindowWidth(), getOriginalImageWidth() * getZoom(), showScrollbars);
        scrollbarsPanel.setVerticalPosition(posY, getWindowHeight(), getOriginalImageHeight() * getZoom(), showScrollbars);
    }

    private void scaleBy(double dScale) {
        double newScale;
        if (getZoom() * dScale > getZoomMax()) {
            newScale = getOriginalImageWidth() / getWindowWidth() * (getZoomMax());
        } else if (getScale() * dScale > getScaleMin()) {
            newScale = getScale() * dScale;
        } else {
            newScale = getScaleMin();
        }

        double lastCenterX = imgX * getZoom() + getWindowWidth() / 2;
        double lastCenterY = imgY * getZoom() + getWindowHeight() / 2;

        double newCenterX = lastCenterX * newScale / getScale();
        double newCenterY = lastCenterY * newScale / getScale();

        int newImgX = (int) (newCenterX - getWindowWidth() / 2);
        int newImgY = (int) (newCenterY - getWindowHeight() / 2);

        imgX = newImgX / getZoom(newScale);
        imgY = newImgY / getZoom(newScale);

        setScale(newScale);

    }

    private void checkImageCoords() {

        if (imgX + getOriginalImageWidth() / getScale() > getOriginalImageWidth()) {
            imgX = getOriginalImageWidth() - (int) (getOriginalImageWidth() / getScale()) - 1;
        }

        double height = (getOriginalImageWidth() * getWindowHeight() / getWindowWidth());

        if (imgY + height / getScale() > getOriginalImageHeight()) {
            imgY = getOriginalImageHeight() - (int) (height / getScale()) - 1;
        }

        if (imgX < 0) {
            imgX = 0;
        }

        if (imgY < 0) {
            imgY = 0;
        }
    }

    private void centerImage() {

        imgX = (getOriginalImageWidth() - getWindowWidth() * getScale()) / 2 / getScale();
        imgY = (getOriginalImageHeight() - getWindowHeight() * getScale()) / 2 / getScale();
    }

    @Override
    public void zoomIn() {
        scaleBy(getScaleStep());
        redraw(true);
    }

    @Override
    public void zoomOut() {
        scaleBy(1.0d / getScaleStep());
        redraw(true);
    }
}
