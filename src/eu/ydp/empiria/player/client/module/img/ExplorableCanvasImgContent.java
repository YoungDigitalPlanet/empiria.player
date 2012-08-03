package eu.ydp.empiria.player.client.module.img;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.canvasadapter.client.CanvasAdapter;
import eu.ydp.canvasadapter.client.Context2dAdapter;
import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.components.PanelWithScrollbars;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.NumberUtils;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class ExplorableCanvasImgContent extends Composite implements ImgContent {


	private static ExplorableCanvasImgContentUiBinder uiBinder = GWT.create(ExplorableCanvasImgContentUiBinder.class);

	interface ExplorableCanvasImgContentUiBinder extends UiBinder<Widget, ExplorableCanvasImgContent> {	}

	@UiField
	protected FlowPanel mainPanel;
	@UiField
	protected FlowPanel windowPanel;
	@UiField
	protected FlowPanel imagePanel;
	@UiField
	protected CanvasAdapter imageCanvas;
	@UiField
	protected PushButton zoominButton;
	@UiField
	protected PushButton zoomoutButton;
	@UiField
	protected PanelWithScrollbars scrollbarsPanel;

	private Image tempImage;
	private final Context2dAdapter context2d;
	private final int REDRAW_INTERVAL_MIN = 50;
	private FocusWidget focusCanvas;
	private double scale = 2.0d;
	private double scaleMin = 1.0d;
	private final double ZOOM_MAX = 8;
	private final double SCALE_STEP = 1.2d;
	private double imgX = 0;
	private double imgY = 0;
	private double prevX, prevY;
	private boolean moving = false;
	private boolean touchingButtons = false;
	private boolean zoomInClicked = false;
	private double originalImageWidth, originalImageHeight;
	private double originalImageAspectRatio;
	private double windowWidth = 300, windowHeight = 300;
	private double prevDistance = -1;
	private Map<String, String> styles;
	private long lastRedrawTime = -1;
	private boolean imageLoaded = false;
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	private final Timer startZoomTimer;
	private final Timer zoomTimer;

	public ExplorableCanvasImgContent() {
		initWidget(uiBinder.createAndBindUi(this));
		context2d = imageCanvas.getContext2d();

		startZoomTimer = new Timer() {

			@Override
			public void run() {
				zoomTimer.scheduleRepeating(200);
			}
		};

		zoomTimer = new Timer() {

			@Override
			public void run() {
				zoom();
			}
		};
	}

	@Override
	public void init(Element element, ModuleSocket ms) {
		tempImage = new Image(element.getAttribute("src"));
		RootPanel.get().add(tempImage);
		// TODO: try to put img on a div with visibility:hidden
		// see http://gwt-image-loader.googlecode.com/svn/trunk/src/com/reveregroup/gwt/imagepreloader/ImagePreloader.java
		if (!Navigator.getUserAgent().contains("MSIE")) {
			tempImage.setVisible(false);
		}
		tempImage.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				imageLoaded = true;
				originalImageWidth = tempImage.getWidth();
				originalImageHeight = tempImage.getHeight();
				originalImageAspectRatio = originalImageWidth / originalImageHeight;
				if (windowHeight/originalImageHeight  <  windowWidth/originalImageWidth) {
					scaleMin = 1.0d * originalImageWidth / originalImageHeight;
				}
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

		styles = ms.getStyles(element);

		String toReplace = "\\D";
		if (styles.containsKey(EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL)){
			scale = (double)(NumberUtils.tryParseInt(styles.get(EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL).replaceAll(toReplace, ""), 100))/100.0d;
		}
		if (styles.containsKey(EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH)){
			windowWidth = NumberUtils.tryParseInt(styles.get(EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH).replaceAll(toReplace, ""), 300);
		}
		if (styles.containsKey(EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT)){
			windowHeight = NumberUtils.tryParseInt(styles.get(EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT).replaceAll(toReplace, ""), 300);
		}

		imageCanvas.setCoordinateSpaceWidth((int)windowWidth);
		imageCanvas.setCoordinateSpaceHeight((int)windowHeight);
		imageCanvas.setWidth(((int)windowWidth) + "px");
		imageCanvas.setHeight(((int)windowHeight) + "px");
		scrollbarsPanel.setSize(((int)windowWidth) + "px", ((int)windowHeight) + "px");
		focusCanvas = (FocusWidget)imageCanvas.asWidget();
		focusCanvas.addTouchStartHandler(new TouchStartHandler() {

			@Override
			public void onTouchStart(TouchStartEvent event) {
				onMoveStart(event.getTouches().get(0).getClientX(), event.getTouches().get(0).getClientY());
				event.preventDefault();
				//rezerwujemy touch dla siebie nic innego nie powinno obslugiwac tego zdarzenia np TouchPageSwitch
				eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
			}
		});
		focusCanvas.addTouchMoveHandler(new TouchMoveHandler() {

			@Override
			public void onTouchMove(TouchMoveEvent event) {
				if (event.getTouches().length() == 1){
					onMoveMove(event.getTouches().get(0).getClientX(), event.getTouches().get(0).getClientY());
				} else if (event.getTouches().length() == 2){
					onMoveScale(event.getTouches().get(0).getClientX(), event.getTouches().get(0).getClientY(), event.getTouches().get(1).getClientX(), event.getTouches().get(1).getClientY());
				}
				event.preventDefault();
			}

		});
		focusCanvas.addTouchEndHandler(new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				onMoveEnd();
				event.preventDefault();
			}
		});

		focusCanvas.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				onMoveStart(event.getClientX(), event.getClientY());
			}
		});

		focusCanvas.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				onMoveMove(event.getClientX(), event.getClientY());
			}
		});

		focusCanvas.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				onMoveEnd();
			}
		});

		focusCanvas.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				onMoveEnd();
			}
		});

	}

	private void onMoveStart(int x, int y){
		moving = true;
		prevX = x;
		prevY = y;
	}

	private void onMoveScale(int x1, int y1, int x2, int y2) {
		final double currDistance= Math.sqrt(
				Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)
				);

		if (prevDistance != -1){
			scaleBy(currDistance/prevDistance);
			redraw(true);
		}

		prevDistance = currDistance;

	}

	private void onMoveMove(int x, int y){
		if (moving){

			double dx = x - prevX;
			double dy = y - prevY;


			double zoom  = getZoom();

			imgX -= dx/zoom;
			imgY -= dy/zoom;

			redraw(true);

			prevX = x;
			prevY = y;

		}
	}

	private double getZoom(){
		return windowWidth/ originalImageWidth * (scale) ;
	}

	private void onMoveEnd(){
		moving = false;
		prevDistance = -1;
	}

	private void checkImageCoords(){

		if (imgX + originalImageWidth / scale > originalImageWidth) {
			imgX = originalImageWidth - (int)(originalImageWidth / scale) - 1;
		}

		double h = (originalImageWidth * windowHeight / windowWidth);

		if (imgY + h / scale > originalImageHeight) {
			imgY = originalImageHeight - (int)(h / scale) - 1;
		}

		if (imgX < 0) {
			imgX = 0;
		}

		if (imgY < 0) {
			imgY = 0;
		}
	}

	private void centerImage(){

		imgX = (originalImageWidth - windowWidth * scale)/2 / scale;
		imgY = (originalImageHeight - windowHeight * scale)/2 / scale;
	}

	private void redraw(boolean showScrollbars){

		checkImageCoords();

		double scaleNormalized = scale;

		double sourceX = imgX;
		double sourceY = imgY;
		double sourceWidth = originalImageWidth / scaleNormalized;
		double sourceHeight = (originalImageWidth * windowHeight / windowWidth) / scaleNormalized;
		double destX = 0;
		double destY = 0;
		double destWidth = windowWidth;
		double destHeight = windowHeight;

		if (sourceX + sourceWidth >  originalImageWidth){
			sourceWidth = originalImageWidth - sourceX;
			double z = getZoom();
			destWidth = sourceWidth * z;
			context2d.clearRect(destWidth, 0, windowWidth - destWidth, windowHeight);
		}
		if (sourceY + sourceHeight >  originalImageHeight){
			sourceHeight = originalImageHeight - sourceY;
			double z = getZoom();
			destHeight = sourceHeight * z;
			context2d.clearRect(0, destHeight, windowWidth, windowHeight - destHeight);
		}

		if ( System.currentTimeMillis() - lastRedrawTime > REDRAW_INTERVAL_MIN){
			if (imageLoaded) {
				context2d.drawImage(ImageElement.as(tempImage.getElement()), sourceX, sourceY, sourceWidth, sourceHeight, destX, destY, destWidth, destHeight);
			}
			lastRedrawTime = System.currentTimeMillis();
			updateScrollbars(showScrollbars);
		}
	}


	@UiHandler("zoominButton")
	public void zoomInButtonMouseDownHandler(MouseDownEvent event){
		if (!touchingButtons) {
			zoomIn();
		}
	}

	@UiHandler("zoominButton")
	public void zoomInButtonTouchStartHandler(TouchStartEvent event){
		zoomIn();
		touchingButtons = true;
		startZoomTimer.schedule(500);
	}

	@UiHandler("zoominButton")
	public void zoomInButtonMouseUpHandler(MouseUpEvent event){
		cancelZoomTimers();
	}

	@UiHandler("zoominButton")
	public void zoomInButtonTouchEndHandler(TouchEndEvent event){
		cancelZoomTimers();
	}

	@UiHandler("zoomoutButton")
	public void zoomOutButtonMouseDownHandler(MouseDownEvent event){
		if (!touchingButtons) {
			zoomOut();
		}
	}

	@UiHandler("zoomoutButton")
	public void zoomOutButtonTouchStartHandler(TouchStartEvent event){
		zoomOut();
		touchingButtons = true;
		startZoomTimer.schedule(500);
	}

	@UiHandler("zoomoutButton")
	public void zoomOutButtonMouseUpHandler(MouseUpEvent event){
		cancelZoomTimers();
	}

	@UiHandler("zoomoutButton")
	public void zoomOutButtonTouchEndHandler(TouchEndEvent event){
		cancelZoomTimers();
	}

	private void zoomIn() {
		zoomInClicked = true;
		zoom();
	}

	private void zoomOut() {
		zoomInClicked = false;
		zoom();
	}

	private void zoom(){
		if (zoomInClicked){
			scaleBy(SCALE_STEP);
		} else {
			scaleBy(1.0d/SCALE_STEP);
		}
		redraw(true);
	}

	private void scaleBy(double dScale){
		double newScale;
		if (getZoom()*dScale > ZOOM_MAX) {
			newScale = originalImageWidth / windowWidth * (ZOOM_MAX);
		} else if (scale * dScale > scaleMin) {
			newScale = scale*dScale;
		} else {
			newScale = scaleMin;
		}

		double realDScaleNormalized = 1.0d/scale - 1.0d/newScale;

		imgX += originalImageWidth * (realDScaleNormalized/2);
		imgY += originalImageHeight * (realDScaleNormalized/2);

		scale = newScale;

	}

	private void cancelZoomTimers() {
		zoomTimer.cancel();
		startZoomTimer.cancel();
	}

	private void updateScrollbars(boolean showScrollbars){
		double posX = imgX*getZoom();
		double posY = imgY*getZoom();
		scrollbarsPanel.setHorizontalPosition(posX, windowWidth, originalImageWidth*getZoom(), showScrollbars);
		scrollbarsPanel.setVerticalPosition(posY, windowHeight, originalImageHeight*getZoom(), showScrollbars);
	}

}
