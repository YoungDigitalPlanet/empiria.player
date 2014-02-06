package eu.ydp.empiria.player.client.module.media.html5.reattachhack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.annotation.Nullable;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.AttachHandlerFactory;
import eu.ydp.empiria.player.client.module.media.html5.AttachHandlerImpl;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class HTML5VideoReattachHackJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private HTML5VideoReattachHack hack;
	private HTML5VideoMediaWrapper mediaWrapper;
	private HTML5VideoMediaExecutor mediaExecutor;
	private EventsBus eventBus;
	private HTML5VideoRebuilder videoRebuilder;
	private Video video;

	@Before
	public void setUpBefore() throws Exception {
		setUpGin();
		setUpInjectors();
		mockMedia();
	}

	@Test
	public void checkAttachHandlerAttachedToNewVideoInstance() {
		// when
		hack.reAttachVideo(mediaWrapper, mediaExecutor);

		// than
		verify(video).addAttachHandler((AttachHandlerImpl) anyObject());
	}

	@Test
	public void checkIsNewVideoCreatedUsingProperCreator() {
		// when
		hack.reAttachVideo(mediaWrapper, mediaExecutor);

		// than
		verify(videoRebuilder).getVideo();
	}

	@Test
	public void checkIsNewVideoMediaWrapperSetAndWrapperInitialized() {
		// when
		hack.reAttachVideo(mediaWrapper, mediaExecutor);

		// than
		verify(mediaExecutor).setMediaWrapper((HTML5VideoMediaWrapper) anyObject());
		verify(mediaExecutor).init();
	}

	@Test
	public void testMediaEventsFired() {
		// when
		hack.reAttachVideo(mediaWrapper, mediaExecutor);

		// than
		ArgumentCaptor<MediaEvent> arguments = ArgumentCaptor.forClass(MediaEvent.class);

		verify(eventBus, times(2)).fireAsyncEventFromSource(arguments.capture(), (AbstractHTML5MediaWrapper) anyObject());

		List<MediaEvent> mediaEvents = arguments.getAllValues();
		assertThat(Iterables.transform(mediaEvents, toTypesListConverter()), containsInAnyOrder(MediaEventTypes.ON_TIME_UPDATE, MediaEventTypes.ON_PAUSE));
	}

	private Function<MediaEvent, MediaEventTypes> toTypesListConverter() {
		return new Function<MediaEvent, MediaEventTypes>() {

			@Override
			@Nullable
			public MediaEventTypes apply(@Nullable MediaEvent mediaEvent) {
				return mediaEvent.getType();
			}
		};
	}

	private void setUpInjectors() {
		eventBus = injector.getInstance(EventsBus.class);
		hack = injector.getInstance(HTML5VideoReattachHack.class);
		videoRebuilder = injector.getInstance(HTML5VideoRebuilder.class);
	}

	private void mockMedia() {
		video = mock(Video.class);
		when(videoRebuilder.getVideo()).thenReturn(video);

		mediaWrapper = mock(HTML5VideoMediaWrapper.class);
		when(mediaWrapper.getMediaObject()).thenReturn(video);
		mediaExecutor = mock(HTML5VideoMediaExecutor.class);
	}

	private void setUpGin() {
		GuiceModuleConfiguration config = new GuiceModuleConfiguration();
		config.addAllClassToSpy(EventsBus.class);
		setUpAndOverrideMainModule(config, new CustomGinModule());
	}

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(AttachHandlerFactory.class).toInstance(mock(AttachHandlerFactory.class));
			binder.bind(HTML5VideoRebuilder.class).toInstance(mock(HTML5VideoRebuilder.class));
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
