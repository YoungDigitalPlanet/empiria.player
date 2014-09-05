package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;

@RunWith(GwtMockitoTestRunner.class)
public class HTML5VideoMediaExecutorJUnitTest extends AbstractHTML5MediaExecutorJUnitBase {

	private static final String NARRATION_TEXT = "text";
	private static final String POSTER_URL = "poster";
	private static final int WIDTH = 30;
	private static final int HEIGHT = 20;
	private TextTrack track;

	@Override
	@Before
	public void before() {
		setUpGuice();
		super.before();
	}

	@Override
	public AbstractHTML5MediaExecutor getExecutorInstanceMock() {
		return spy(injector.getInstance(HTML5VideoMediaExecutor.class));
	}

	@Override
	public MediaBase getMediaBaseMock() {
		Video videMock = mock(Video.class);
		track = mock(TextTrack.class);
		doReturn(track).when(videMock).addTrack(Matchers.any(TextTrackKind.class));
		return videMock;
	}

	@Override
	public BaseMediaConfiguration getBaseMediaConfiguration() {
		Map<String, String> sources = Maps.newHashMap();
		sources.put("http://dummy", "video/mp4");
		return new BaseMediaConfiguration(sources, MediaType.VIDEO, POSTER_URL, HEIGHT, WIDTH, true, true, NARRATION_TEXT);
	}

	@Test
	public void testInitExecutor() {
		instance.setBaseMediaConfiguration(mediaConfiguration);
		instance.init();
		verify(mediaBase).setHeight(Matchers.eq(HEIGHT + "px"));
		verify(mediaBase).setWidth(Matchers.eq(WIDTH + "px"));
		verify((Video) mediaBase).setPoster(Matchers.eq(POSTER_URL));
		verify((Video) mediaBase).addTrack(Matchers.eq(TextTrackKind.SUBTITLES));
		verify(track).addCue(Matchers.any(TextTrackCue.class));
	}
}
