package eu.ydp.empiria.player.client.module.img;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.ModuleSocket;

public class ExplorableImgContent extends Composite implements ImgContent {

	private static ZoomableImgContentUiBinder uiBinder = GWT.create(ZoomableImgContentUiBinder.class);

	interface ZoomableImgContentUiBinder extends UiBinder<Widget, ExplorableImgContent> { }
	
	@UiField
	FlowPanel mainPanel;
	@UiField
	FlowPanel windowPanel;
	@UiField
	FlowPanel imagePanel;
	@UiField
	Image image;
	@UiField
	PushButton zoominButton;
	@UiField
	PushButton zoomoutButton;
	@UiField
	Label consoleLabel;
	
	protected final static double ZOOM_COEFF = 1.2d;

	protected int originalImageWidth;
	protected int originalImageHeight;
	protected int currentImageWidth;
	protected int currentImageHeight;
	protected double zoom = 0;
	protected int minZoom;
	
	private Timer startZoomTimer;
	private Timer zoomTimer;	
	private boolean zoomInClicked;
	private boolean touchingButtons = false;
	
	private double lastDistance = -1;
	
	private long lastTouchTime = -1;
	private int lastTouchX;
	private int lastTouchY;
	private final int DOUBLE_TOUCH_INTERVAL_MAX = 300;
	private final int DOUBLE_TOUCH_DISTANCE_MAX = 50;

	public ExplorableImgContent() {
		initWidget(uiBinder.createAndBindUi(this));
		windowPanel.getElement().getStyle().setOverflow(Overflow.SCROLL);
		startZoomTimer = new Timer() {
			
			@Override
			public void run() {
				zoomTimer.scheduleRepeating(300);
					
			}
		};
		zoomTimer = new Timer() {
			
			@Override
			public void run() {
				doZoom(zoomInClicked);
			}
		};
	}

	@Override
	public void init(Element element, ModuleSocket moduleSocket) {
		image.setUrl(element.getAttribute("src"));
		
		image.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				originalImageWidth = image.getWidth();
				originalImageHeight = image.getHeight();
			}
		});
		
		image.addTouchStartHandler(new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				
				if (event.getTouches().length() == 1){
				
					if (lastTouchTime != -1  &&  (new Date()).getTime() - lastTouchTime < DOUBLE_TOUCH_INTERVAL_MAX  &&  
							Math.abs(event.getTouches().get(0).getClientX() - lastTouchX) < DOUBLE_TOUCH_DISTANCE_MAX  &&
							Math.abs(event.getTouches().get(0).getClientY() - lastTouchY) < DOUBLE_TOUCH_DISTANCE_MAX){
						doBigZoom();
						lastTouchTime = -1;
					} else {
						lastTouchTime = (new Date()).getTime();
						lastTouchX = event.getTouches().get(0).getClientX();
						lastTouchY = event.getTouches().get(0).getClientY();
					}
				}
			}
		});
		
		image.addTouchEndHandler(new TouchEndHandler() {
			
			@Override
			public void onTouchEnd(TouchEndEvent event) {
				lastDistance = -1;
			}
		});
		
		image.addTouchMoveHandler(new TouchMoveHandler() {
			
			@Override
			public void onTouchMove(TouchMoveEvent event) {

				if (event.getTargetTouches().length() == 2){
					event.preventDefault();
					double newDistance = Math.sqrt( 
							Math.pow(event.getTargetTouches().get(0).getClientX() - event.getTargetTouches().get(1).getClientX(), 2) + 
							Math.pow(event.getTargetTouches().get(0).getClientY() - event.getTargetTouches().get(1).getClientY(), 2)
							);
					if (lastDistance != -1){
						double windowWidth = windowPanel.getOffsetWidth();
						double windowHeight = windowPanel.getOffsetHeight();
						double oldScrollLeft = windowPanel.getElement().getScrollLeft() + windowWidth/2;
						double oldScrollWidth = windowPanel.getElement().getScrollWidth();
						double oldScrollTop = windowPanel.getElement().getScrollTop() + windowHeight/2;
						double oldScrollHeight = windowPanel.getElement().getScrollHeight();
						
						double previousZoom = zoom;
						
						doSpecifiedZoom(newDistance/lastDistance, previousZoom);

						double newScrollWidth = windowPanel.getElement().getScrollWidth();
						double newScrollHeight = windowPanel.getElement().getScrollHeight();
						
						double newScrollLeft = newScrollWidth/oldScrollWidth*oldScrollLeft;
						double newScrollTop = newScrollHeight/oldScrollHeight*oldScrollTop;
						windowPanel.getElement().setScrollLeft((int)(newScrollLeft-windowWidth/2));
						windowPanel.getElement().setScrollTop((int)(newScrollTop-windowHeight/2));						
					}
					lastDistance = newDistance;
					
				}
			}
		});
		
		image.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				doBigZoom();
			}
		});
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
		touchingButtons = true;
		zoomOut();
	}

	@UiHandler("zoomoutButton")
	public void zoomOutButtonMouseUpHandler(MouseUpEvent event){
		cancelZoomTimers();
	}
	
	@UiHandler("zoomoutButton")
	public void zoomOutButtonTouchEndHandler(TouchEndEvent event){
		cancelZoomTimers();
	}
	
	private void zoomIn(){
		doZoom(true);
		startZoomTimer.schedule(750);
	}
	
	private void zoomOut(){
		doZoom(false);
		startZoomTimer.schedule(750);
	}
	
	private void cancelZoomTimers(){
		startZoomTimer.cancel();
		zoomTimer.cancel();
	}
	
	private void doBigZoom(){
		if (zoom < 5 ){
			zoom += 5;
			doSpecifiedZoom(zoom - 5);
		} else {
			zoom -= 5;
			doSpecifiedZoom(zoom + 5);					
		}
	}
	
	private void doZoom(boolean in){
		zoomInClicked = in;
		double previousZoom = zoom;
		if (in )
			zoom = Math.round(++zoom);
		else
			zoom = Math.round(--zoom);
		doSpecifiedZoom(previousZoom);
	}

	private void doSpecifiedZoom(double previousZoom){
		double scale = Math.pow(ZOOM_COEFF, zoom);
		applySpecifiedScaleOrRestore(scale, previousZoom);
	}
	private void doSpecifiedZoom(double dScale, double previousZoom){
		double scale = Math.pow(ZOOM_COEFF, zoom)*dScale;
		double newZoom = Math.log(scale)/Math.log(ZOOM_COEFF) ;
		consoleLabel.setText("dScale: " + dScale + " zoom: " + zoom + " newZoom: " + newZoom);
		zoom = newZoom;
		applySpecifiedScaleOrRestore(scale, previousZoom);
	}
	private void applySpecifiedScaleOrRestore(double specifiedScale, double previousZoom){
		int newWidth = (int)Math.round(originalImageWidth*specifiedScale);
		int newHeight = (int)Math.round(originalImageHeight*specifiedScale);
		if (newWidth > windowPanel.getOffsetWidth()  ||  newHeight > windowPanel.getOffsetHeight()){
			double windowWidth = windowPanel.getOffsetWidth();
			double windowHeight = windowPanel.getOffsetHeight();
			double oldScrollLeft = windowPanel.getElement().getScrollLeft() + windowWidth/2;
			double oldScrollWidth = currentImageWidth;
			double oldScrollTop = windowPanel.getElement().getScrollTop() + windowHeight/2;
			double oldScrollHeight = currentImageHeight;
			image.setSize(String.valueOf(newWidth)+"px", String.valueOf(newHeight)+"px");
			currentImageWidth = newWidth;
			currentImageHeight = newHeight;
			double newScrollWidth = currentImageWidth;
			double newScrollHeight = currentImageHeight;
			
			windowPanel.getElement().setScrollLeft((int)(newScrollWidth/oldScrollWidth*oldScrollLeft-windowWidth/2));
			windowPanel.getElement().setScrollTop((int)(newScrollHeight/oldScrollHeight*oldScrollTop-windowHeight/2));			
		}else{
			zoom = previousZoom;
		}
		
	}

}
