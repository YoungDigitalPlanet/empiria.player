package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwtmockito.GwtMockito;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;

@RunWith(GwtMockitoTestRunner.class)
public class HTML5AudioMediaExecutorJUnitTest extends AbstractHTML5MediaExecutorJUnitBase {

	@Before
	public void setUp() {
		GwtMockito.initMocks(this);
		instance = (AbstractHTML5MediaExecutor) new HTML5AudioMediaExecutor(mediaEventMapper, html5MediaNativeListeners);
		mediaBase = mock(Audio.class);

		Map<String, String> sources = Maps.newHashMap();
		sources.put("http://dummy", "audio/mp4");

		mediaConfiguration = mock(BaseMediaConfiguration.class);
		when(mediaConfiguration.getSources()).thenReturn(sources);
		when(mediaConfiguration.getMediaType()).thenReturn(MediaType.AUDIO);
		when(mediaConfiguration.isTemplate()).thenReturn(true);
	}

	@Override
	public String getAssumedMediaPreloadType() {
		return MediaElement.PRELOAD_AUTO;
	}
}
