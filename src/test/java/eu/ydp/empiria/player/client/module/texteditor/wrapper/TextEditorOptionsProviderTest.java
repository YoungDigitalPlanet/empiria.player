package eu.ydp.empiria.player.client.module.texteditor.wrapper;

import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextEditorOptionsProviderTest {

	@InjectMocks
	private TextEditorOptionsProvider testObj;
	@Mock
	private UserAgentUtil userAgentUtil;
	@Mock
	private TextEditorDesktopOptions desktopOptions;
	@Mock
	private TextEditorMobileOptions mobileOptions;

	@Test
	public void shouldReturnMobileOptions() {
		// given
		when(userAgentUtil.isMobileUserAgent()).thenReturn(true);

		// when
		TextEditorOptions actual = testObj.get();

		// then
		assertThat(actual).isInstanceOf(TextEditorMobileOptions.class);
	}

	@Test
	public void shouldReturnDesktopOptions() {
		// given
		when(userAgentUtil.isMobileUserAgent()).thenReturn(false);

		// when
		TextEditorOptions actual = testObj.get();

		// then
		assertThat(actual).isInstanceOf(TextEditorDesktopOptions.class);
	}
}