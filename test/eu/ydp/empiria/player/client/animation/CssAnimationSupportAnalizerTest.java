package eu.ydp.empiria.player.client.animation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;

import eu.ydp.gwtutil.client.util.UserAgentChecker.BrowserUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

@RunWith(MockitoJUnitRunner.class)
public class CssAnimationSupportAnalizerTest {
	List<MobileUserAgent> mobileUserAgents = Lists.newArrayList(
									MobileUserAgent.CHROME,
									MobileUserAgent.SAFARI,
									MobileUserAgent.FIREFOX,
									MobileUserAgent.ANDROID4);

	List<UserAgent> desktopUserAgents = Lists.newArrayList(
									UserAgent.GECKO1_8,
									UserAgent.SAFARI);

	private @Mock UserAgentUtil userAgentUtil;
	private @InjectMocks CssAnimationSupportAnalizer supportAnalizer;

	@Test
	public void isCssAnimationSupportedMobile() throws Exception {
		doReturn(true).when(userAgentUtil).isMobileUserAgent();

		for(MobileUserAgent ua : MobileUserAgent.values()){
			mockMobileUserAgent(ua);
			assertThat(supportAnalizer.isCssAnimationSupported()).isEqualTo(mobileUserAgents.contains(ua));
		}
	}

	@Test
	public void isCssAnimationSupportedDesktop() throws Exception {
		doReturn(false).when(userAgentUtil).isMobileUserAgent();

		for(UserAgent ua : UserAgent.values()){
			mockDesktopUserAgent(ua);
			assertThat(supportAnalizer.isCssAnimationSupported()).isEqualTo(desktopUserAgents.contains(ua));
		}
	}

	private void mockMobileUserAgent(final BrowserUserAgent userAgnet) {
		when(userAgentUtil.isMobileUserAgent(((MobileUserAgent[]) anyVararg()))).then(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				Object[] arguments =invocation.getArguments();
				return Lists.newArrayList(arguments).contains(userAgnet);
			}
		});
	}

	private void mockDesktopUserAgent(final BrowserUserAgent userAgnet) {
		when(userAgentUtil.isUserAgent(((UserAgent[]) anyVararg()))).then(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				Object[] arguments =invocation.getArguments();
				return Lists.newArrayList(arguments).contains(userAgnet);
			}
		});
	}

}
