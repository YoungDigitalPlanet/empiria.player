package eu.ydp.empiria.player.client.module.info.progress;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.info.ContentFieldRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PercentResultProviderTest {

    private static final String ITEM_RESULT_KEY = "$[item.result]";
    private static final String TEST_RESULT_KEY = "$[test.result]";

    @InjectMocks
    private PercentResultProvider testObj;
    @Mock
    private ContentFieldRegistry fieldRegistry;
    @Mock
    private ContentFieldInfo fieldInfo;

    @Before
    public void init() {
        Optional<ContentFieldInfo> fieldInfoOptional = Optional.of(fieldInfo);
        when(fieldRegistry.getFieldInfo(ITEM_RESULT_KEY)).thenReturn(fieldInfoOptional);
        when(fieldRegistry.getFieldInfo(TEST_RESULT_KEY)).thenReturn(fieldInfoOptional);
    }

    @Test
    public void shouldReturnIntegerTestResult() {
        // given
        int expectedTestResult = 50;
        String percentStringTestResult = "50";
        when(fieldInfo.getValue(isA(Integer.class))).thenReturn(percentStringTestResult);

        // when
        int testResult = testObj.getTestResult();

        // then
        assertThat(testResult).isEqualTo(expectedTestResult);
    }

    @Test
    public void shouldReturnItemResult() {
        // given
        int itemIndex = 3;
        int expectedItemResult = 50;
        String percentStringItemResult = "50";
        when(fieldInfo.getValue(itemIndex)).thenReturn(percentStringItemResult);

        // when
        int testResult = testObj.getItemResult(itemIndex);

        // then
        assertThat(testResult).isEqualTo(expectedItemResult);
    }

}