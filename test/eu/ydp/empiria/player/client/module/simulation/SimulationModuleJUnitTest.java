package eu.ydp.empiria.player.client.module.simulation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.module.simulation.SimulationModule.TouchReservationHandler;
import eu.ydp.empiria.player.client.preloader.Preloader;
import eu.ydp.empiria.player.client.preloader.view.ProgressView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.handler.ManifestLoadHandler;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;
import eu.ydp.gwtcreatejs.client.loader.Manifest;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest(CreateJsLoader.class)
public class SimulationModuleJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static final String URL = "http://dummyurl/";

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(ProgressView.class).toInstance(createProgressViewMock());
			binder.bind(SimulationModuleView.class).toInstance(mock(SimulationModuleView.class));
			binder.bind(CreateJsLoader.class).toInstance(createJsLoaderMock());
			binder.bind(Preloader.class).toInstance(spy(new Preloader()));
		}

		private ProgressView createProgressViewMock() {
			ProgressView progressView = mock(ProgressView.class);
			when(progressView.asWidget()).thenReturn(mock(Widget.class));
			return progressView;
		}

		private CreateJsLoader createJsLoaderMock() {
			CreateJsLoader loader = mock(CreateJsLoader.class);
			return loader;
		}
	}

	private SimulationModule instance;
	private CreateJsLoader createJsLoader;
	private SimulationModuleView moduleView;
	private Preloader preloader;
	private EventsBus eventsBus;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	private Element createElementMock() {
		Element element = mock(Element.class);
		doReturn(URL).when(element).getAttribute(Mockito.anyString());
		return element;
	}

	@Before
	public void before() {
		GuiceModuleConfiguration config = new GuiceModuleConfiguration();
		config.addClassWithDisabledPostConstruct(Preloader.class);
		config.addAllClassToSpy(EventsBus.class);
		setUpAndOverrideMainModule(config, new CustomGinModule());
		instance = spy(injector.getInstance(SimulationModule.class));
		createJsLoader = injector.getInstance(CreateJsLoader.class);
		moduleView = injector.getInstance(SimulationModuleView.class);
		preloader = injector.getInstance(Preloader.class);
		eventsBus = injector.getInstance(EventsBus.class);
	}

	@Test
	public void testInitModuleElement() {
		instance.initModule(createElementMock());
		verify(createJsLoader).addManifestLoadHandler(Mockito.any(ManifestLoadHandler.class));
		verify(createJsLoader).setLibraryURL(Mockito.eq("http://dummyurl/../../../common/jslibs/"));
		verify(createJsLoader).addCompleteHandler(Mockito.any(CompleteHandler.class));
		verify(createJsLoader).load(Mockito.eq(URL));
	}

	@Test
	public void testGetNewInstance() {
		assertNotNull(instance.getNewInstance());
	}

	@Test
	public void testGetView() {
		instance.getView();
		verify(moduleView).asWidget();
	}

	@Test
	public void testOnBodyLoad() {
		instance.onBodyLoad();
		verify(instance).onBodyLoad();
		Mockito.verifyNoMoreInteractions(instance);
	}

	@Test
	public void testOnBodyUnload() {
		instance.onBodyUnload();
		verify(instance).onBodyUnload();
		Mockito.verifyNoMoreInteractions(instance);
	}

	@Test
	public void testOnSetUp() {
		instance.onSetUp();
		verify(instance).onSetUp();
		Mockito.verifyNoMoreInteractions(instance);
	}

	@Test
	public void testOnStart() {
		instance.onStart();
		verify(instance).onStart();
		Mockito.verifyNoMoreInteractions(instance);
	}

	@Test
	public void testOnClose() {
		instance.initModule(createElementMock());
		instance.onClose();
		verify(createJsLoader).stopSounds();
	}

	@Test
	public void testOnManifestLoad() {
		Manifest manifest = mock(Manifest.class);
		double width = 50d;
		doReturn(width).when(manifest).getWidth();
		double height = 70d;
		doReturn(height).when(manifest).getHeight();

		instance.onManifestLoad(manifest);
		verify(moduleView).add(Mockito.eq(preloader));
		verify(preloader).setPreloaderSize(Mockito.eq((int) width), Mockito.eq((int) height));
		verify(preloader).show();

	}

	@Test
	public void initializeCanvasTest(){
		Canvas canvas = Mockito.mock(Canvas.class);
		instance.initializeCanvas(canvas);

		verify(canvas).addTouchStartHandler(Mockito.any(TouchStartHandler.class));
		verify(instance).addChildView(Mockito.eq(canvas));
		verify(preloader).hide();
		verify(preloader.asWidget()).removeFromParent();

	}

	@Test
	public void touchReservationTest(){
		Canvas canvas = Mockito.mock(Canvas.class);
		ArgumentCaptor<TouchReservationHandler> touchReservationCaptor = ArgumentCaptor.forClass(TouchReservationHandler.class);
		ArgumentCaptor<PlayerEvent> playerEventCaptor = ArgumentCaptor.forClass(PlayerEvent.class);

		instance.initializeCanvas(canvas);
		verify(canvas).addTouchStartHandler(touchReservationCaptor.capture());
		TouchReservationHandler reservationHandler = touchReservationCaptor.getValue();
		reservationHandler.onTouchStart(null);
		verify(eventsBus).fireAsyncEvent(playerEventCaptor.capture());

		assertTrue(playerEventCaptor.getValue().getType() == PlayerEventTypes.TOUCH_EVENT_RESERVATION);
	}
}
