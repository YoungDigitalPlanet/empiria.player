package eu.ydp.empiria.player.client.module.media.html5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.media.client.MediaBase;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.factory.MediaWrapperFactory;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@SuppressWarnings("PMD")
public class HTML5VideoMediaWrapperJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.install(new FactoryModuleBuilder().build(MediaWrapperFactory.class));
		}
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	private HTML5VideoMediaWrapper instance;
	private Media video;
	private EventsBus eventsBus;

	@Before
	public void before() {
		createMediaMock();
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
		eventsBus = injector.getInstance(EventsBus.class);
		instance = injector.getInstance(MediaWrapperFactory.class).getHtml5VideoMediaWrapper(video);
	}

	private void createMediaMock() {
		video = mock(Media.class);
		MediaBase mediaBase = mock(MediaBase.class);
		when(video.getMedia()).thenReturn(mediaBase);
	}

	@Test
	public void canPlayTest_DurationChangeAccurred() {
		assertFalse(instance.canPlay());
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, instance), instance);
		assertTrue(instance.canPlay());
	}

	@Test
	public void canPlayTest_MediaReadyStateHaveNothing() {
		when(video.getMedia().getReadyState()).thenReturn(MediaElement.HAVE_NOTHING);
		assertFalse(instance.canPlay());
	}

	@Test
	public void canPlayTest_MediaReadyStateHaveData() {
		when(video.getMedia().getReadyState()).thenReturn(MediaElement.HAVE_ENOUGH_DATA);
		assertTrue(instance.canPlay());
	}

	@Test
	public void canPlayTest_MediaObjectWasChange() {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, instance), instance);
		assertTrue(instance.canPlay());

		when(video.getMedia().getReadyState()).thenReturn(MediaElement.HAVE_NOTHING);
		instance.setMediaObject(video.getMedia());
		assertFalse(instance.canPlay());
	}

}
