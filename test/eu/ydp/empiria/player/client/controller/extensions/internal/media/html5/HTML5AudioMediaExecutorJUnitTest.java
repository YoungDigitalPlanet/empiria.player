package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.mockito.Mockito.*;

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
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
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
	public AbstractHTML5MediaExecutor<?> getExecutorInstanceMock() {
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

}
