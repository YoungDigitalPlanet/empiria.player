package eu.ydp.empiria.player.client.module.info.progress;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.info.progress.InfoModuleCssProgressParser;
import eu.ydp.empiria.player.client.module.info.progress.InfoModuleProgressMapping;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleProgressMappingTest {

    private InfoModuleProgressMapping testObj;
    @Mock
    private InfoModuleCssProgressParser cssMappingParser;
    private ProgressToStringRangeMap progressToStyleName;

    String styleName = "xxx";

    @Before
    public void setUp() {
        progressToStyleName = new ProgressToStringRangeMap();
        testObj = new InfoModuleProgressMapping(cssMappingParser, progressToStyleName);
    }


    @Test
    public void getStyleNameForProgress_FullRange() throws Exception {
        Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
        doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
        progressToStyleMapping.put(0, styleName);
        testObj.postConstruct();

        String styleNameFromCss = testObj.getStyleNameForProgress(10);
        assertThat(styleNameFromCss).isEqualTo(styleName);
    }

    @Test
    public void getStyleNameForProgress_NumberOfRange() throws Exception {
        Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
        doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
        progressToStyleMapping.put(0, styleName + 0);
        progressToStyleMapping.put(2, styleName + 2);
        progressToStyleMapping.put(9, styleName + 9);

        testObj.postConstruct();

        String styleNameFromCss = testObj.getStyleNameForProgress(1);
        assertThat(styleNameFromCss).isEqualTo(styleName + 0);
        styleNameFromCss = testObj.getStyleNameForProgress(2);
        assertThat(styleNameFromCss).isEqualTo(styleName + 2);
        styleNameFromCss = testObj.getStyleNameForProgress(7);
        assertThat(styleNameFromCss).isEqualTo(styleName + 2);
        styleNameFromCss = testObj.getStyleNameForProgress(9);
        assertThat(styleNameFromCss).isEqualTo(styleName + 9);
        styleNameFromCss = testObj.getStyleNameForProgress(19);
        assertThat(styleNameFromCss).isEqualTo(styleName + 9);
        styleNameFromCss = testObj.getStyleNameForProgress(100);
        assertThat(styleNameFromCss).isEqualTo(styleName + 9);
    }

    @Test
    public void getStyleNameForProgress_ZeroPercentNotDefined() throws Exception {
        Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
        doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
        String defaultStyleName = styleName + 10;
        progressToStyleMapping.put(10, defaultStyleName);

        testObj.postConstruct();

        for (int x = 0; x < 10; ++x) {
            String styleNameFromCss = testObj.getStyleNameForProgress(x);
            assertThat(styleNameFromCss).isNotNull();
            assertThat(styleNameFromCss).isEmpty();

        }

        for (int x = 10; x < 100; ++x) {
            String styleNameFromCss = testObj.getStyleNameForProgress(x);
            assertThat(styleNameFromCss).isNotNull();
            assertThat(styleNameFromCss).isEqualTo(defaultStyleName);

        }
    }

    @Test
    public void getStyleNameForProgress_BoundaryValues() throws Exception {
        Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
        doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
        String defaultStyleName = styleName + 0;
        String maxPercentStyleName = styleName + 100;
        progressToStyleMapping.put(0, defaultStyleName);
        progressToStyleMapping.put(100, maxPercentStyleName);

        testObj.postConstruct();

        for (int x = 0; x < 100; ++x) {
            String styleNameFromCss = testObj.getStyleNameForProgress(x);
            assertThat(styleNameFromCss).isEqualTo(defaultStyleName);
        }
        String styleNameFromCss = testObj.getStyleNameForProgress(100);
        assertThat(styleNameFromCss).isEqualTo(maxPercentStyleName);
    }

    @Test
    public void getStyleNameForProgress_NoDefinedStyles() throws Exception {
        Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
        doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();

        testObj.postConstruct();

        for (int x = 0; x < 100; ++x) {
            String styleNameFromCss = testObj.getStyleNameForProgress(x);
            assertThat(styleNameFromCss).isNotNull();
            assertThat(styleNameFromCss).isEmpty();
        }
    }

    @Test
    public void getStyleNameForProgress_OutOfPercentScope() throws Exception {
        Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
        doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();

        testObj.postConstruct();
        List<Integer> wrongValues = Lists.newArrayList(Integer.MIN_VALUE, -897, -9, -1, 101, 987, Integer.MAX_VALUE);
        for (Integer percent : wrongValues) {
            String styleNameFromCss = testObj.getStyleNameForProgress(percent);
            assertThat(styleNameFromCss).isNotNull();
            assertThat(styleNameFromCss).isEmpty();
        }
    }

    @Test
    public void getStyleNameForProgress_NullProgress() throws Exception {
        Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
        doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
        progressToStyleMapping.put(0, styleName);
        testObj.postConstruct();

        String styleNameFromCss = testObj.getStyleNameForProgress(null);
        assertThat(styleNameFromCss).isNotNull();
        assertThat(styleNameFromCss).isEmpty();
    }
}
