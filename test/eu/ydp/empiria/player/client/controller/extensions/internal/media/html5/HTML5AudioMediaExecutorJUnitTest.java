package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.mockito.Mockito.*;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest({ MediaBase.class, HTML5MediaEvent.class, RootPanel.class, TouchStartEvent.class})
public class HTML5AudioMediaExecutorJUnitTest extends AbstractHTML5MediaExecutorJUnitBase {

	private final ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
	
	@Override
	@Before
	public void before() {
		setUpGuice();
		super.before();
	}

	@Override
	public AbstractHTML5MediaExecutor getExecutorInstanceMock() {
		return spy(injector.getInstance(HTML5AudioMediaExecutor.class));
	}

	@Override
	public MediaBase getMediaBaseMock() {
		return mock(MediaBase.class);
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
		Audio media = Mockito.mock(Audio.class);
		RootPanelDelegate rootPanelDelegate = Mockito.mock(RootPanelDelegate.class);
		RootPanel rootPanel = Mockito.mock(RootPanel.class);
		TouchStartEvent event = Mockito.mock(TouchStartEvent.class);
		
		when(userAgentUtil.isInsideIframe())
			.thenReturn(true);
		
		when(userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI))
			.thenReturn(true);
		
		when(rootPanelDelegate.getRootPanel())
			.thenReturn(rootPanel);
		
		HandlerRegistration mockHandlerRegistration = Mockito.mock(HandlerRegistration.class);
		when(rootPanel.addDomHandler(any(TouchStartHandler.class), eq(TouchStartEvent.getType())))
			.thenReturn(mockHandlerRegistration);
		
		HTML5AudioMediaExecutor audioMediaExecutor = new HTML5AudioMediaExecutor(userAgentUtil, rootPanelDelegate);
		audioMediaExecutor.setMedia(media);
		audioMediaExecutor.onTouchStart(event);
		
		
		verify(rootPanel).addDomHandler(any(TouchStartHandler.class), eq(TouchStartEvent.getType()));
		verify(mockHandlerRegistration).removeHandler();
		verify(media).play();
	}

}
