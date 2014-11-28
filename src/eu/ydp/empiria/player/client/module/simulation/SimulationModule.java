package eu.ydp.empiria.player.client.module.simulation;

import static eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes.PAGE_CHANGE;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.handler.ManifestLoadHandler;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;
import eu.ydp.gwtcreatejs.client.loader.Manifest;

public class SimulationModule extends SimpleModuleBase implements ILifecycleModule, ManifestLoadHandler, PlayerEventHandler {

	protected final class TouchReservationHandler implements TouchStartHandler {
		@Override
		public void onTouchStart(TouchStartEvent event) {
			touchController.setTouchReservation(true);
		}
	}

	protected CreateJsLoader loader;
	@Inject
	private EventsBus eventBus;
	@Inject
	private TouchController touchController;
	@Inject
	private Instance<SimulationModuleView> viewInstance;
	@Inject
	private SimulationPreloader preloader;
	@Inject
	private Instance<CreateJsLoader> createJsLoader;
	@Inject
	private PageScopeFactory pageScopeFactory;
	@Inject
	private SimulationController simulationController;
	@Inject
	private SimulationCanvasProvider simulationCanvasProvider;
	private int pageIndex = -1;

	@Override
	public Widget getView() {
		return getSimulationModuleView().asWidget();
	}

	@Override
	public void onBodyLoad() {
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onClose() {
		if (loader != null) {
			loader.stopSounds();
		}
	}

	private void addPageChangeHandlers() {
		eventBus.addHandler(PlayerEvent.getTypes(PAGE_CHANGE), this);
	}

	@Override
	protected void initModule(Element element) {
		String src = element.getAttribute("src");
		addPageChangeHandlers();
		initializeLoader(src);
		pageIndex = pageScopeFactory.getCurrentPageScope().getPageIndex();
	}

	protected void initializeLoader(String resourceSrc) {
		loader = createJsLoader.get();
		loader.addManifestLoadHandler(this);
		loader.setLibraryURL(getLibraryURL(resourceSrc));
		loader.addCompleteHandler(new CompleteHandler() {

			@Override
			public void onComplete() {
				Optional<Canvas> simulationCanvas = simulationCanvasProvider.getSimulationCanvas(loader);
				if (simulationCanvas.isPresent()) {
					initializeCanvas(simulationCanvas.get());
				}
			}
		});
		loader.load(resourceSrc);
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
		preloader.show(preloaderWidth, preloaderHeight);
	}

	private void hidePreloader() {
		preloader.hidePreloaderAndRemoveFromParent();
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		Optional<com.google.gwt.user.client.Element> simulationCanvasElement = simulationCanvasProvider.getSimulationCanvasElement(loader);

		if (event.getType() == PAGE_CHANGE && simulationCanvasElement.isPresent()) {
			if (Objects.equal(pageIndex, event.getValue())) {
				simulationController.resumeAnimation(simulationCanvasElement.get());
			} else {
				simulationController.pauseAnimation(simulationCanvasElement.get());
			}
		}
	}
}
