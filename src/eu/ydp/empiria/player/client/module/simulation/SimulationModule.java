package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.preloader.Preloader;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.handler.ManifestLoadHandler;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;
import eu.ydp.gwtcreatejs.client.loader.Manifest;


public class SimulationModule extends SimpleModuleBase implements Factory<SimulationModule>, ILifecycleModule, ManifestLoadHandler {

	protected final class TouchReservationHandler implements TouchStartHandler {
		@Override
		public void onTouchStart(TouchStartEvent event) {
			eventBus.fireAsyncEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
		}
	}

	protected CreateJsLoader loader;

	@Inject
	private EventsBus eventBus;

	@Inject
	private Provider<SimulationModule> simulationModuleProvider;

	@Inject
	private Instance<SimulationModuleView> viewInstance;

	@Inject
	private Preloader preloader;

	@Inject
	private Instance<CreateJsLoader> createJsLoader;

	@Override
	public SimulationModule getNewInstance() {
		return simulationModuleProvider.get();
	}

	@Override
	public Widget getView() {
		return getSimulationModuleView().asWidget();
	}

	@Override
	public void onBodyLoad() {
		//
	}

	@Override
	public void onBodyUnload() {
		//
	}

	@Override
	public void onSetUp() {
		//
	}

	@Override
	public void onStart() {
		//
	}

	@Override
	public void onClose() {
		if (loader != null) {
			loader.stopSounds();
		}
	}

	@Override
	protected void initModule(Element element) {
		String src = element.getAttribute("src");

		initializeLoader(src);
		loader.load(src);
	}

	protected void initializeLoader(String resourceSrc) {
		loader = createJsLoader.get();
		loader.addManifestLoadHandler(this);
		loader.setLibraryURL(getLibraryURL(resourceSrc));
		loader.addCompleteHandler(new CompleteHandler() {

			@Override
			public void onComplete() {
				initializeCanvas(loader.getContent().getCanvas());
			}
		});
	}

	protected void initializeCanvas(Canvas canvas) {
		hidePreloader();
		canvas.addTouchStartHandler(new TouchReservationHandler());
		addChildView(canvas);
	}

	protected SimulationModuleView getSimulationModuleView() {
		return viewInstance.get();
	}

	protected void addChildView(IsWidget child) {
		SimulationModuleView parentView = getSimulationModuleView();
		if (parentView != null) {
			parentView.add(child);
		}
	}

	protected String getLibraryURL(String resourceSrc) {
		StringBuilder libraryURL = new StringBuilder(resourceSrc.substring(0, resourceSrc.lastIndexOf('/') + 1));

		// FIXME path to libraries should be given from extension
		libraryURL.append("../../../common/jslibs/");
		return libraryURL.toString();
	}

	@Override
	public void onManifestLoad(Manifest manifest) {
		showPreloader((int) manifest.getWidth(), (int) manifest.getHeight());
	}

	private void showPreloader(int preloaderWidth, int preloaderHeight) {
		addChildView(preloader);
		preloader.setPreloaderSize(preloaderWidth, preloaderHeight);
		preloader.show();
	}

	private void hidePreloader() {
		preloader.hide();
		preloader.asWidget().removeFromParent();
	}
}
