package eu.ydp.empiria.player.client.module.simulation;

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
import eu.ydp.empiria.player.client.module.core.flow.LifecycleModule;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsPlugin;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.handler.ManifestLoadHandler;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;
import eu.ydp.gwtcreatejs.client.loader.Manifest;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.PAGE_CHANGE;
import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.WINDOW_RESIZED;

public class SimulationModule extends SimpleModuleBase implements LifecycleModule, ManifestLoadHandler, PlayerEventHandler {

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

    @Inject
    private SoundJsPlugin soundJsPlugin;

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

    private void addResizeWindowHandlers() {
        eventBus.addAsyncHandler(PlayerEvent.getType(WINDOW_RESIZED), this);
    }

    @Override
    protected void initModule(Element element) {
        String src = element.getAttribute("src");
        addPageChangeHandlers();
        addResizeWindowHandlers();
        initializeLoader(src);
        pageIndex = pageScopeFactory.getCurrentPageScope()
                .getPageIndex();
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

        if (simulationCanvasElement.isPresent()) {
            com.google.gwt.user.client.Element canvasElement = simulationCanvasElement.get();

            switch (event.getType()) {
                case PAGE_CHANGE:
                    pageChangeEventProceed(event, canvasElement);
                    break;
                case WINDOW_RESIZED:
                    windowResizedEventProceed(canvasElement);
                    break;
                default:
                    break;
            }
        }
    }

    private void windowResizedEventProceed(com.google.gwt.user.client.Element canvasElement) {
        simulationController.onWindowResized(canvasElement);
    }

    private void pageChangeEventProceed(PlayerEvent event, com.google.gwt.user.client.Element canvasElement) {
        if (Objects.equal(pageIndex, event.getValue())) {
            simulationController.resumeAnimation(canvasElement);
        } else {
            simulationController.pauseAnimation(canvasElement);
        }
    }
}
