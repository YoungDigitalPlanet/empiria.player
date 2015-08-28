package eu.ydp.empiria.player.client.module.img.explorable.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.img.explorable.ExplorableImageConst;
import eu.ydp.empiria.player.client.module.img.explorable.ExplorableImgWindow;
import eu.ydp.empiria.player.client.module.img.explorable.ExplorableImgWindowProvider;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.Map;

public class ExplorableImgContentViewImpl extends Composite implements ExplorableImgContentView, ImgContent {

    private static ExplorableImgContentUiBinder uiBinder = GWT.create(ExplorableImgContentUiBinder.class);
    @UiField(provided = true)
    ExplorableImgWindow window;
    @UiField
    FlowPanel mainPanel;
    @UiField
    FlowPanel windowPanel;
    @UiField
    PushButton zoominButton;
    @UiField
    PushButton zoomoutButton;
    @UiField
    FlowPanel toolbox;

    private int windowWidth = ExplorableImageConst.WINDOW_WIDTH;
    private int windowHeight = ExplorableImageConst.WINDOW_HEIGHT;
    private double scale = ExplorableImageConst.ZOOM_SCALE;
    private double scaleStep = ExplorableImageConst.ZOOM_SCALE_STEP;
    private double zoomMax = ExplorableImageConst.ZOOM_SCALE_MAX;

    @Inject
    public ExplorableImgContentViewImpl(ExplorableImgWindowProvider explorableImgWindowProvider) {
        window = explorableImgWindowProvider.get();
        initUiBinder();
    }

    @Inject
    private StyleSocket styleSocket;

    private void initUiBinder() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private void parseStyles(Map<String, String> styles) {
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

    @Override
    public void init(Element element, ModuleSocket moduleSocket) {
        parseStyles(styleSocket.getStyles(element));

        Element titleNodes = XMLUtils.getFirstElementWithTagName(element, EmpiriaTagConstants.ATTR_TITLE);
        final String title = XMLUtils.getTextFromChilds(titleNodes);
        window.init(windowWidth, windowHeight, element.getAttribute(EmpiriaTagConstants.ATTR_SRC), scale, scaleStep, zoomMax, title);
    }

    @Override
    public void zoomIn() {
        window.zoomIn();
    }

    @Override
    public void zoomOut() {
        window.zoomOut();
    }

    @Override
    public void registerCommandOnToolbox(EventHandlerProxy handler) {
        handler.apply(toolbox);
    }

    @Override
    public void registerZoomInButtonCommands(EventHandlerProxy handler) {
        handler.apply(zoominButton);
    }

    @Override
    public void registerZoomOutButtonCommands(EventHandlerProxy handler) {
        handler.apply(zoomoutButton);
    }


    interface ExplorableImgContentUiBinder extends UiBinder<Widget, ExplorableImgContentViewImpl> {
    }

}
