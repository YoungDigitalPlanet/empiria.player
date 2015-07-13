package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleProgressStyleNameTest {
    private static final String FIELD_INFO_KEY = "$[item.result]";

    @Mock
    private InfoModuleProgressMapping infoModuleProgressMapping;
    @Mock
    private ContentFieldRegistry fieldRegistry;
    @InjectMocks
    private InfoModuleProgressStyleName instance;

    @Test
    public void getCurrentStyleName_NullValueFromFieldInfo() throws Exception {
        ContentFieldInfo fieldInfo = mock(ContentFieldInfo.class);

        doReturn(Optional.<ContentFieldInfo>of(fieldInfo)).when(fieldRegistry).getFieldInfo(eq(FIELD_INFO_KEY));
        instance.getCurrentStyleName(21);
        verify(infoModuleProgressMapping).getStyleNameForProgress(any(Integer.class));
    }

    @Test
    public void getCurrentStyleName_CorrectValueFromFieldInfo() throws Exception {
        int percent = 21;
        String toReturnStyleName = "styleName";

        ContentFieldInfo fieldInfo = mock(ContentFieldInfo.class);
        doReturn(String.valueOf(percent)).when(fieldInfo).getValue(anyInt());
        doReturn(Optional.<ContentFieldInfo>of(fieldInfo)).when(fieldRegistry).getFieldInfo(eq(FIELD_INFO_KEY));
        doReturn(toReturnStyleName).when(infoModuleProgressMapping).getStyleNameForProgress(eq(percent));

        String styleName = instance.getCurrentStyleName(percent);
        assertThat(toReturnStyleName).isEqualTo(styleName);
        verify(infoModuleProgressMapping).getStyleNameForProgress(eq(percent));
    }

}
