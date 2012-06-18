package eu.ydp.empiria.player.client.module.img;

import java.util.Date;
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
import eu.ydp.empiria.player.client.components.PanelWithScrollbars;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.IntegerUtils;

public class ExplorableImgContent extends Composite implements ImgContent {

	private static ExplorableCanvasImgContentUiBinder uiBinder = GWT.create(ExplorableCanvasImgContentUiBinder.class);

	interface ExplorableCanvasImgContentUiBinder extends UiBinder<Widget, ExplorableImgContent> {	}

	@UiField
	FlowPanel mainPanel;
	@UiField
	FlowPanel windowPanel;
	@UiField
	PushButton zoominButton;
	@UiField
	PushButton zoomoutButton;
	@UiField
	ExplorableImgWindow window;

	private Image tempImage;
	
	private int windowWidth = 300, windowHeight = 300;
	private Map<String, String> styles;
	private double scale = 2.0d;
	private boolean touchingButtons = false;
	private boolean zoomInClicked = false;

	private Timer startZoomTimer;
	private Timer zoomTimer;	
	
	public ExplorableImgContent() {
		initWidget(uiBinder.createAndBindUi(this));
		
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
		
		styles = ms.getStyles(element);

		if (styles.containsKey("-empiria-img-explorable-scale-initial")){
			scale = (double)(IntegerUtils.tryParseInt(styles.get("-empiria-img-explorable-scale-initial").replaceAll("\\D", ""), 100))/100.0d;
		}
		if (styles.containsKey("-empiria-img-explorable-window-width")){
			windowWidth = IntegerUtils.tryParseInt(styles.get("-empiria-img-explorable-window-width").replaceAll("\\D", ""), 300);
		}
		if (styles.containsKey("-empiria-img-explorable-window-height")){
			windowHeight = IntegerUtils.tryParseInt(styles.get("-empiria-img-explorable-window-height").replaceAll("\\D", ""), 300);
		}

		window.init(windowWidth, windowHeight, element.getAttribute("src"), scale);
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
			window.zoomIn();
		} else {
			window.zoomOut();
		}
	}
	

	private void cancelZoomTimers() {
		zoomTimer.cancel();
		startZoomTimer.cancel();
	}

}
