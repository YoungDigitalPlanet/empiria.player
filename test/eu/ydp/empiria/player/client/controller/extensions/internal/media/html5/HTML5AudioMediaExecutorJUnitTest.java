package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.configuration.MockitoConfiguration;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest({ MediaBase.class, HTML5MediaEvent.class, RootPanel.class, TouchStartEvent.class})
public class HTML5AudioMediaExecutorJUnitTest extends AbstractHTML5MediaExecutorJUnitBase {

	@Override
	@Before
	public void before() {
		MockitoConfiguration.setenableClassCache(false);
		setUpGuice();
		super.before();
	}

	@Override
	public AbstractHTML5MediaExecutor getExecutorInstanceMock() {
		return spy(injector.getInstance(HTML5AudioMediaExecutor.class));
	}

	@Override
	public MediaBase getMediaBaseMock() {
		return mock(Audio.class);
	}

	@Override
	public BaseMediaConfiguration getBaseMediaConfiguration() {
		Map<String, String> sources = Maps.newHashMap();
		sources.put("http://dummy", "audio/mp4");
		return  new BaseMediaConfiguration(sources, MediaType.AUDIO, "poster", 20, 30, true, true, "text");
	}

	@Test
	public void testInitExecutor() {
		Mockito.reset(instance);
		instance.initExecutor();
		verify(instance).initExecutor();
		verifyNoMoreInteractions(instance);
	}

	@Test
	public void shouldApplyPlayOnTouchIosHackWhenInIframe() throws Exception {
		UserAgentUtil userAgentUtil = Mockito.mock(UserAgentUtil.class);

		when(userAgentUtil.isInsideIframe())
			.thenReturn(true);

		when(userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI))
			.thenReturn(true);

		Instance<IosAudioPlayHack> iosPlayHack = createIosAudoHackMock();
		HTML5AudioMediaExecutor audioMediaExecutor = new HTML5AudioMediaExecutor(iosPlayHack,userAgentUtil);
		verify(iosPlayHack.get()).applyHack(eq(audioMediaExecutor));


	}

	public Instance<IosAudioPlayHack> createIosAudoHackMock(){
		Instance<IosAudioPlayHack> iosPlayHack = mock(Instance.class);
		IosAudioPlayHack iosHack = mock(IosAudioPlayHack.class);
		doReturn(iosHack).when(iosPlayHack).get();
		return iosPlayHack;

	}

}
