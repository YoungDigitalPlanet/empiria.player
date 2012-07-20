package eu.ydp.empiria.player.client.module.img;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class ExplorableImgContent extends Composite implements ImgContent {
	private static final String ZOOMOUT_BUTTON = "zoomoutButton";
	private static final String ZOOMIN_BUTTON = "zoominButton";
	private static ExplorableCanvasImgContentUiBinder uiBinder = GWT.create(ExplorableCanvasImgContentUiBinder.class);

	interface ExplorableCanvasImgContentUiBinder extends UiBinder<Widget, ExplorableImgContent> {	}

	@UiField
	protected FlowPanel mainPanel;
	@UiField
	protected FlowPanel windowPanel;
	@UiField
	protected PushButton zoominButton;
	@UiField
	protected PushButton zoomoutButton;
	@UiField
	protected ExplorableImgWindow window;

	private int windowWidth = 300, windowHeight = 300;
	private Map<String, String> styles;
	private double scale = 2.0d;
	private boolean touchingButtons = false;
	private boolean zoomInClicked = false;

	private final Timer startZoomTimer;
	private final Timer zoomTimer;



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
	public void init(Element element, ModuleSocket moduleSocket) {

		styles = moduleSocket.getStyles(element);

		String toReplace = "\\D";
		if (styles.containsKey(EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL)){
			scale = (double)(IntegerUtils.tryParseInt(styles.get(EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL).replaceAll(toReplace, ""), 100))/100.0d;
		}
		if (styles.containsKey(EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH)){
			windowWidth = IntegerUtils.tryParseInt(styles.get(EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH).replaceAll(toReplace, ""), 300);
		}
		if (styles.containsKey(EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT)){
			windowHeight = IntegerUtils.tryParseInt(styles.get(EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT).replaceAll(toReplace, ""), 300);
		}
		Element titleNodes = XMLUtils.getFirstElementWithTagName(element, "title");
		final String title  =XMLUtils.getTextFromChilds(titleNodes);
		window.init(windowWidth, windowHeight, element.getAttribute("src"), scale,title);
	}


	@UiHandler(ZOOMIN_BUTTON)
	public void zoomInButtonMouseDownHandler(MouseDownEvent event){
		if (!touchingButtons) {
			zoomIn();
		}
	}

	@UiHandler(ZOOMIN_BUTTON)
	public void zoomInButtonTouchStartHandler(TouchStartEvent event){
		zoomIn();
		touchingButtons = true;
		startZoomTimer.schedule(500);
	}

	@UiHandler(ZOOMIN_BUTTON)
	public void zoomInButtonMouseUpHandler(MouseUpEvent event){
		cancelZoomTimers();
	}

	@UiHandler(ZOOMIN_BUTTON)
	public void zoomInButtonTouchEndHandler(TouchEndEvent event){
		cancelZoomTimers();
	}

	@UiHandler(ZOOMOUT_BUTTON)
	public void zoomOutButtonMouseDownHandler(MouseDownEvent event){
		if (!touchingButtons) {
			zoomOut();
		}
	}

	@UiHandler(ZOOMOUT_BUTTON)
	public void zoomOutButtonTouchStartHandler(TouchStartEvent event){
		zoomOut();
		touchingButtons = true;
		startZoomTimer.schedule(500);
	}

	@UiHandler(ZOOMOUT_BUTTON)
	public void zoomOutButtonMouseUpHandler(MouseUpEvent event){
		cancelZoomTimers();
	}

	@UiHandler(ZOOMOUT_BUTTON)
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
