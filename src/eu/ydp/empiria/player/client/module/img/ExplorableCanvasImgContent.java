package eu.ydp.empiria.player.client.module.img;

import java.util.Date;
import java.util.Map;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Overflow;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.canvasadapter.client.CanvasAdapter;
import eu.ydp.canvasadapter.client.Context2dAdapter;
import eu.ydp.empiria.player.client.components.PanelWithScrollbars;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.IntegerUtils;

public class ExplorableCanvasImgContent extends Composite implements ImgContent {

	private static ExplorableCanvasImgContentUiBinder uiBinder = GWT.create(ExplorableCanvasImgContentUiBinder.class);

	interface ExplorableCanvasImgContentUiBinder extends UiBinder<Widget, ExplorableCanvasImgContent> {	}

	@UiField
	FlowPanel mainPanel;
	@UiField
	FlowPanel windowPanel;
	@UiField
	FlowPanel imagePanel;
	@UiField
	CanvasAdapter imageCanvas;
	@UiField
	PushButton zoominButton;
	@UiField
	PushButton zoomoutButton;
	@UiField
	Label consoleLabel;
	@UiField
	Image tempImage;
	@UiField
	Panel tempPanel;
	@UiField
	PanelWithScrollbars scrollbarsPanel;
	
	private Context2dAdapter context2d;
	private final int REDRAW_INTERVAL_MIN = 50;
	private FocusWidget focusCanvas;
	private double scale = 200.0d;
	private double scaleMin = 100.0d;
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

	private Timer startZoomTimer;
	private Timer zoomTimer;	
	
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
		tempPanel.setSize("5px", "5px");
		tempPanel.getElement().getStyle().setOverflow(Overflow.HIDDEN);
		tempImage.setUrl(element.getAttribute("src"));
		tempImage.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				originalImageWidth = tempImage.getOffsetWidth();
				originalImageHeight = tempImage.getOffsetHeight();
				originalImageAspectRatio = (double)originalImageWidth / (double)originalImageHeight;
				if (windowHeight/originalImageHeight  <  windowWidth/originalImageWidth)
					scaleMin = 100.0d * (double)originalImageWidth / (double)originalImageHeight;
				centerImage();
				redraw(false);
			}
		});
		
		styles = ms.getStyles(element);

		if (styles.containsKey("-empiria-img-explorable-scale-initial")){
			scale = (double)(IntegerUtils.tryParseInt(styles.get("-empiria-img-explorable-scale-initial").replaceAll("\\D", ""), 100));
		}
		if (styles.containsKey("-empiria-img-explorable-window-width")){
			windowWidth = IntegerUtils.tryParseInt(styles.get("-empiria-img-explorable-window-width").replaceAll("\\D", ""), 300);
		}
		if (styles.containsKey("-empiria-img-explorable-window-height")){
			windowHeight = IntegerUtils.tryParseInt(styles.get("-empiria-img-explorable-window-height").replaceAll("\\D", ""), 300);
		}
		
		imageCanvas.setCoordinateSpaceWidth((int)windowWidth);
		imageCanvas.setCoordinateSpaceHeight((int)windowHeight);
		imageCanvas.setWidth(String.valueOf((int)windowWidth) + "px");
		imageCanvas.setHeight(String.valueOf((int)windowHeight) + "px");
		scrollbarsPanel.setSize(String.valueOf((int)windowWidth) + "px", String.valueOf((int)windowHeight) + "px");
		focusCanvas = (FocusWidget)imageCanvas.asWidget();
		focusCanvas.addTouchStartHandler(new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				onMoveStart(event.getTouches().get(0).getClientX(), event.getTouches().get(0).getClientY());
				event.preventDefault();
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
		double currDistance= Math.sqrt(
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
		return (double)windowWidth/ (double)originalImageWidth * (scale/100.0d) ;
	}
	
	private void onMoveEnd(){
		moving = false;
		prevDistance = -1;
	}
	
	private void checkImageCoords(){
		
		double scaleNormalized = scale/100.0d;

		if (imgX + originalImageWidth / scaleNormalized > originalImageWidth)
			imgX = originalImageWidth - (int)(originalImageWidth / scaleNormalized) - 1;	
		
		double h = (originalImageWidth * windowHeight / windowWidth);
		
		if (imgY + h / scaleNormalized > originalImageHeight)
			imgY = originalImageHeight - (int)(h / scaleNormalized) - 1; 
		
		if (imgX < 0)
			imgX = 0;
		
		if (imgY < 0)
			imgY = 0;	
	}
	
	private void centerImage(){
		double scaleNormalized = scale/100.0d;

		imgX = (originalImageWidth - windowWidth * scaleNormalized)/2 / scaleNormalized;
		imgY = (originalImageHeight - windowHeight * scaleNormalized)/2 / scaleNormalized; 
	}
	
	private void redraw(boolean showScrollbars){
		
		checkImageCoords();
		
		double scaleNormalized = scale/100.0d;
		
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
		
		if ((new Date()).getTime() - lastRedrawTime > REDRAW_INTERVAL_MIN){
			context2d.drawImage(ImageElement.as(tempImage.getElement()), sourceX, sourceY, sourceWidth, sourceHeight, destX, destY, destWidth, destHeight);
			lastRedrawTime = (new Date()).getTime();
			updateScrollbars(showScrollbars);
		}
	}
	

	@UiHandler("zoominButton")
	public void zoomInButtonMouseDownHandler(MouseDownEvent event){
		if (!touchingButtons)
			zoomIn();
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
		if (!touchingButtons)
			zoomOut();
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
		if (getZoom()*dScale > ZOOM_MAX)
			newScale = (double)originalImageWidth / (double)windowWidth * (ZOOM_MAX*100.0d);
		else if (scale * dScale > scaleMin)
			newScale = scale*dScale;
		else
			newScale = scaleMin;
		
		double realDScaleNormalized = 100.0d/scale - 100.0d/newScale;
		
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
