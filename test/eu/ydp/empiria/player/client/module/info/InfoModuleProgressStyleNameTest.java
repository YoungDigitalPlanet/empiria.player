package eu.ydp.empiria.player.client.module.info;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleProgressStyleNameTest {
	private static final String TEST_RESULT_KEY = "$[test.result]";

	@Mock private InfoModuleProgressMapping infoModuleProgressMapping;
	@Mock private ContentFieldRegistry fieldRegistry;
	@InjectMocks private InfoModuleProgressStyleName instance;

	@Test
	public void getCurrentStyleName_NullValueFromFiledRegistry() throws Exception {
		for (int x = 0; x < 89; ++x) {
			String currentStyleName = instance.getCurrentStyleName(x);
			assertThat(currentStyleName).isNotNull();
			assertThat(currentStyleName).isEmpty();
		}
	}

	@Test
	public void getCurrentStyleName_NullValueFromFieldInfo() throws Exception {
		ContentFieldInfo fieldInfo = mock(ContentFieldInfo.class);
		doReturn(fieldInfo).when(fieldRegistry).getFieldInfo(eq(TEST_RESULT_KEY));
		instance.getCurrentStyleName(21);
		verify(infoModuleProgressMapping).getStyleNameForProgress(any(Integer.class));
	}

	@Test
	public void getCurrentStyleName_CorrectValueFromFieldInfo() throws Exception {
		int percent = 21;
		String toReturnStyleName = "styleName";

		ContentFieldInfo fieldInfo = mock(ContentFieldInfo.class);
		doReturn("21").when(fieldInfo).getValue(anyInt());
		doReturn(fieldInfo).when(fieldRegistry).getFieldInfo(eq(TEST_RESULT_KEY));
		doReturn(toReturnStyleName).when(infoModuleProgressMapping).getStyleNameForProgress(eq(percent));


		String styleName = instance.getCurrentStyleName(percent);
		assertThat(toReturnStyleName).isEqualTo(styleName);
		verify(infoModuleProgressMapping).getStyleNameForProgress(eq(percent));
	}

}
