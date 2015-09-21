package eu.ydp.empiria.player.client.module.info.progress;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.gin.factory.InfoModuleFactory;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.gwtutil.client.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleProgressStyleNameTest {
    private static final String EMPIRIA_INFO_ITEM_RESULT = "-empiria-info-item-result-";
    private static final String EMPIRIA_INFO_TEST_RESULT = "-empiria-info-test-result-";

    private InfoModuleProgressStyleName testObj;
    @Mock
    private InfoModuleProgressMapping infoModuleProgressMapping;
    @Mock
    private InfoModuleFactory infoModuleFactory;
    @Mock
    private InfoModuleCssProgressParser cssProgressParser;
    @Mock
    private PercentResultProvider percentResultProvider;

    @Before
    public void init() {
        when(infoModuleFactory.getInfoModuleCssProgressParser(EMPIRIA_INFO_ITEM_RESULT)).thenReturn(cssProgressParser);
        when(infoModuleFactory.getInfoModuleCssProgressParser(EMPIRIA_INFO_TEST_RESULT)).thenReturn(cssProgressParser);
        when(infoModuleFactory.getInfoModuleProgressMapping(cssProgressParser)).thenReturn(infoModuleProgressMapping);
        when(infoModuleProgressMapping.getStyleNameForProgress(isA(Integer.class))).thenReturn("");
        testObj = new InfoModuleProgressStyleName(percentResultProvider, infoModuleFactory);
    }

    @Test
    public void shouldReturnItemStyleName(){
        // given
        String expectedStyleName = "item style";
        int itemIndex = 3;
        int itemResult = 50;
        when(percentResultProvider.getItemResult(itemIndex)).thenReturn(itemResult);
        when(infoModuleProgressMapping.getStyleNameForProgress(itemResult)).thenReturn(expectedStyleName);

        // when
        String currentStyleName = testObj.getCurrentStyleName(itemIndex);

        // then
        assertThat(currentStyleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldReturnTestStyleName_whenItemStyleIsEmpty() {
        // given
        String expectedStyleName = "test style";
        int testResult = 50;
        when(percentResultProvider.getTestResult()).thenReturn(testResult);
        when(infoModuleProgressMapping.getStyleNameForProgress(testResult)).thenReturn(expectedStyleName);

        // when
        String currentStyleName = testObj.getCurrentStyleName(isA(Integer.class));

        // then
        assertThat(currentStyleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldReturnEmptyString_whenItemAndTestStylesAreEmpty() {
        // when
        String currentStyleName = testObj.getCurrentStyleName(isA(Integer.class));

        // then
        assertThat(currentStyleName).isEqualTo("");
    }
}
