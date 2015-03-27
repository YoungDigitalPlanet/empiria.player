package eu.ydp.empiria.player.client.module.speechscore.presenter;

import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpeechScoreProtocolProviderTest {

	@InjectMocks
	private SpeechScoreProtocolProvider testObj;

	@Mock
	private UserAgentUtil userAgentUtil;

	@Test
	public void shouldGetHTTPProtocol() {
		// given
		String expected = "http://";
		when(userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI)).thenReturn(false);

		// when
		String actual = testObj.get();

		// then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void shouldGetYDPProtocol() {
		// given
		String expected = "ydpsr://";
		when(userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI)).thenReturn(true);

		// when
		String actual = testObj.get();

		// then
		assertThat(actual).isEqualTo(expected);
	}
}