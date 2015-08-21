package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.Map;

public class ExplorableImgContent extends Composite implements ImgContent {

    private static ExplorableImgContentUiBinder uiBinder = GWT.create(ExplorableImgContentUiBinder.class);

    interface ExplorableImgContentUiBinder extends UiBinder<Widget, ExplorableImgContent> {
    }

    @Inject
    private StyleSocket styleSocket;

    @UiField(provided = true)
    protected ExplorableImgWindow window;


    protected void initUiBinder() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Inject
    public ExplorableImgContent(ExplorableImgWindowProvider explorableImgWindowProvider) {
        window = explorableImgWindowProvider.get();
        initUiBinder();
        toolbox.addDomHandler(new TouchStartHandler() {

            @Override
            public void onTouchStart(TouchStartEvent event) {
                event.preventDefault();
            }
        }, TouchStartEvent.getType());
        startZoomTimer = initializeZoomTimer();
    }

    @Override
    public void init(Element element, ModuleSocket moduleSocket) {
        parseStyles(styleSocket.getStyles(element));

        Element titleNodes = XMLUtils.getFirstElementWithTagName(element, EmpiriaTagConstants.ATTR_TITLE);
        final String title = XMLUtils.getTextFromChilds(titleNodes);
        window.init(windowWidth, windowHeight, element.getAttribute(EmpiriaTagConstants.ATTR_SRC), scale, scaleStep, zoomMax, title);
    }

    protected void zoom() {
        if (zoomInClicked) {
            window.zoomIn();
        } else {
            window.zoomOut();
        }
    }

    protected static final String ZOOMOUT_BUTTON = "zoomoutButton";
    protected static final String ZOOMIN_BUTTON = "zoominButton";
    protected boolean touchingButtons = false;
    protected boolean zoomInClicked = false;

    protected int windowWidth = ExplorableImageConst.WINDOW_WIDTH;
    protected int windowHeight = ExplorableImageConst.WINDOW_HEIGHT;
    protected double scale = ExplorableImageConst.ZOOM_SCALE;
    protected double scaleStep = ExplorableImageConst.ZOOM_SCALE_STEP;
    protected double zoomMax = ExplorableImageConst.ZOOM_SCALE_MAX;
    private final Timer startZoomTimer;
    private Timer zoomTimer;

    @UiField
    protected FlowPanel mainPanel;
    @UiField
    protected FlowPanel windowPanel;
    @UiField
    protected PushButton zoominButton;
    @UiField
    protected PushButton zoomoutButton;
    @UiField
    protected FlowPanel toolbox;


    protected void parseStyles(Map<String, String> styles) {
        String toReplace = "\\D";
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL)) {
            scale = (double) (NumberUtils
                    .tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL).replaceAll(toReplace, ""), 100)) / 100.0d;
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_STEP)) {
            scaleStep = (double) NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_STEP).replaceAll(toReplace, ""), 20) / 100.0d + 1d;
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_MAX)) {
            zoomMax = (double) NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_MAX).replaceAll(toReplace, ""), 800) / 100.0d;
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH)) {
            windowWidth = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH).replaceAll(toReplace, ""), 300);
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT)) {
            windowHeight = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT).replaceAll(toReplace, ""), 300);
        }
    }

    private final Timer initializeZoomTimer() {
        zoomTimer = new Timer() {

            @Override
            public void run() {
                zoom();
            }
        };

        return new Timer() {

            @Override
            public void run() {
                zoomTimer.scheduleRepeating(200);
            }
        };
    }

    protected void cancelZoomTimers() {
        zoomTimer.cancel();
        startZoomTimer.cancel();
    }

    @UiHandler(ZOOMIN_BUTTON)
    public void zoomInButtonMouseDownHandler(MouseDownEvent event) {
        if (!touchingButtons) {
            zoomIn();
        }
        event.preventDefault();
    }

    @UiHandler(ZOOMIN_BUTTON)
    public void zoomInButtonTouchStartHandler(TouchStartEvent event) {
        zoomIn();
        touchingButtons = true;
        startZoomTimer.schedule(500);
        event.preventDefault();
    }

    @UiHandler(ZOOMIN_BUTTON)
    public void zoomInButtonMouseUpHandler(MouseUpEvent event) {
        cancelZoomTimers();
        event.preventDefault();
    }

    @UiHandler(ZOOMIN_BUTTON)
    public void zoomInButtonTouchEndHandler(TouchEndEvent event) {
        cancelZoomTimers();
        event.preventDefault();
    }

    @UiHandler(ZOOMOUT_BUTTON)
    public void zoomOutButtonMouseDownHandler(MouseDownEvent event) {
        if (!touchingButtons) {
            zoomOut();
            event.preventDefault();
        }
    }

    @UiHandler(ZOOMOUT_BUTTON)
    public void zoomOutButtonTouchStartHandler(TouchStartEvent event) {
        zoomOut();
        touchingButtons = true;
        startZoomTimer.schedule(500);
        event.preventDefault();
    }

    @UiHandler(ZOOMOUT_BUTTON)
    public void zoomOutButtonMouseUpHandler(MouseUpEvent event) {
        cancelZoomTimers();
        event.preventDefault();
    }

    @UiHandler(ZOOMOUT_BUTTON)
    public void zoomOutButtonTouchEndHandler(TouchEndEvent event) {
        cancelZoomTimers();
        event.preventDefault();
    }

    private void zoomIn() {
        zoomInClicked = true;
        zoom();
    }

    private void zoomOut() {
        zoomInClicked = false;
        zoom();
    }

}
