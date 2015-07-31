package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.junit.GWTMockUtilities;
import eu.ydp.empiria.player.client.module.img.explorable.AbstractExplorableImgContentBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class AbstractExplorableImgContentBaseJUnitTest {

    private AbstractExplorableImgContentBase explorableImgContent;

    @Before
    public void init() {
        explorableImgContent = mock(AbstractExplorableImgContentBase.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void parseStyles() {
        Map<String, String> styles = new HashMap<String, String>();
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL, "110%");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_STEP, "30%");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_MAX, "500%");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH, "800");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT, "600");

        explorableImgContent.parseStyles(styles);

        assertThat(explorableImgContent.scale, equalTo(1.1d));
        assertThat(explorableImgContent.scaleStep, equalTo(1.3d));
        assertThat(explorableImgContent.zoomMax, equalTo(5d));
        assertThat(explorableImgContent.windowWidth, equalTo(800));
        assertThat(explorableImgContent.windowHeight, equalTo(600));
    }

    @BeforeClass
    public static void prepareTestEnviroment() {
        /**
         * disable GWT.create() behavior for pure JUnit testing
         */
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restoreEnviroment() {
        /**
         * restore GWT.create() behavior
         */
        GWTMockUtilities.restore();
    }
}
