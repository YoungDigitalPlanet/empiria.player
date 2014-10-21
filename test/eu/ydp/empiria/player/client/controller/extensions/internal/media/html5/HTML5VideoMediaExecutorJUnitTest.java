package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwtmockito.GwtMockito;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

@RunWith(GwtMockitoTestRunner.class)
public class HTML5VideoMediaExecutorJUnitTest extends AbstractHTML5MediaExecutorJUnitBase {

	private static final String NARRATION_TEXT = "text";
	private static final String POSTER_URL = "poster";
	private static final int WIDTH = 30;
	private static final int HEIGHT = 20;

	@Mock
	private TextTrack track;

	@Mock
	private UniqueIdGenerator uniqueIdGenerator;

	@Before
	public void setUp() {
		GwtMockito.initMocks(this);

		instance = (AbstractHTML5MediaExecutor) new HTML5VideoMediaExecutor(mediaEventMapper, html5MediaNativeListeners, uniqueIdGenerator, eventsBus);
		Video video = mock(Video.class);
		mediaBase = video;

		Map<String, String> sources = Maps.newHashMap();
		sources.put("http://dummy", "video/mp4");

		mediaConfiguration = mock(BaseMediaConfiguration.class);
		when(mediaConfiguration.getSources()).thenReturn(sources);
		when(mediaConfiguration.getMediaType()).thenReturn(MediaType.VIDEO);
		when(mediaConfiguration.getPoster()).thenReturn(POSTER_URL);
		when(mediaConfiguration.getHeight()).thenReturn(HEIGHT);
		when(mediaConfiguration.getWidth()).thenReturn(WIDTH);
		when(mediaConfiguration.getNarrationText()).thenReturn(NARRATION_TEXT);
		when(mediaConfiguration.isTemplate()).thenReturn(true);
		doReturn(track).when(video).addTrack(Matchers.any(TextTrackKind.class));
	}

	@Test
	public void testInitExecutor() {
		// given
		instance.setBaseMediaConfiguration(mediaConfiguration);
		MediaWrapper<Video> mediaWrapper = mock(MediaWrapper.class);
		when(mediaWrapper.getMediaObject()).thenReturn(mock(Video.class));
		instance.setMedia(mediaBase);

		// when
		instance.initExecutor();

		// then
		verify(mediaBase).setHeight(Matchers.eq(HEIGHT + "px"));
		verify(mediaBase).setWidth(Matchers.eq(WIDTH + "px"));
		verify((Video) mediaBase).setPoster(Matchers.eq(POSTER_URL));
		verify((Video) mediaBase).addTrack(Matchers.eq(TextTrackKind.SUBTITLES));
		verify(track).addCue(Matchers.any(TextTrackCue.class));
	}
}
