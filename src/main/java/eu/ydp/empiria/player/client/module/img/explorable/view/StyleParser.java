package eu.ydp.empiria.player.client.module.img.explorable.view;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.Map;

public class StyleParser {

    @Inject
    private StyleSocket styleSocket;

    public ImageProperties parseStyles(Element element) {

        Map<String, String> styles = styleSocket.getStyles(element);

        ImageProperties imageProperties = new ImageProperties();

        String toReplace = "\\D";
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL)) {
            double scale = (double) (NumberUtils
                    .tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL).replaceAll(toReplace, ""), 100)) / 100.0d;
            imageProperties.setScale(scale);
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_STEP)) {
            double scaleStep = (double) NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_STEP).replaceAll(toReplace, ""), 20) / 100.0d + 1d;
            imageProperties.setScaleStep(scaleStep);
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_MAX)) {
            double zoomMax = (double) NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_MAX).replaceAll(toReplace, ""), 800) / 100.0d;
            imageProperties.setZoomMax(zoomMax);
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH)) {
            int windowWidth = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH).replaceAll(toReplace, ""), 300);
            imageProperties.setWindowWidth(windowWidth);
        }
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT)) {
            int windowHeight = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT).replaceAll(toReplace, ""), 300);
            imageProperties.setWindowHeight(windowHeight);
        }

        return imageProperties;
    }

}
