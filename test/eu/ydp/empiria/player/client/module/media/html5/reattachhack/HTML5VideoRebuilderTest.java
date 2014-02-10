package eu.ydp.empiria.player.client.module.media.html5.reattachhack;

import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.VideoCreator;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;

public class HTML5VideoRebuilderTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private HTML5VideoRebuilder rebuilder;
	private HTML5VideoMediaWrapper mediaWrapper;

	private Video newVideo;
	private FlowPanel parentPanel;
	private VideoCreator videoCreator;
	private Video oldVideo;

	@Before
	public void setUpHTML5VideoRebuilderTest() throws Exception {
		setUpGin();
		createObjectInstances();
		configureRebuilderAndMockNewVideo();
		mockMediaWrapperAndOldVideo();
	}

	@Test
	public void testRecreateVideoWidget() {
		// when
		rebuilder.recreateVideoWidget(mediaWrapper);

		// than
		verify(oldVideo).removeFromParent();
		verify(mediaWrapper).setMediaObject(newVideo);
		verify(parentPanel).insert(newVideo, 0);
	}

	private void configureRebuilderAndMockNewVideo() {
		newVideo = mock(Video.class);
		when(videoCreator.createVideo()).thenReturn(newVideo);
	}

	private void mockMediaWrapperAndOldVideo() {
		oldVideo = mock(Video.class);

		mediaWrapper = mock(HTML5VideoMediaWrapper.class);
		parentPanel = mock(FlowPanel.class);
		when(oldVideo.getParent()).thenReturn(parentPanel);
		when(mediaWrapper.getMediaObject()).thenReturn(oldVideo);
		rebuilder.setVideo(oldVideo);
	}

	private void createObjectInstances() {
		rebuilder = injector.getInstance(HTML5VideoRebuilder.class);
		videoCreator = injector.getInstance(VideoCreator.class);
	}

	private void setUpGin() {
		GuiceModuleConfiguration config = new GuiceModuleConfiguration();
		setUpAndOverrideMainModule(config, new CustomGinModule());
	}

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(VideoCreator.class).toInstance(mock(VideoCreator.class));
			binder.bind(HTML5VideoRebuilder.class).toInstance(new HTML5VideoRebuilder());
		}
	}

	@BeforeClass
	public static void prepareTestEnviroment() {
		/**
		 * disable GWT.create() behavior for pure JUnit testing
		 */
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void restoreEnviroment() {
		/**
		 * restore GWT.create() behavior
		 */
		GWTMockUtilities.restore();
	}

}
