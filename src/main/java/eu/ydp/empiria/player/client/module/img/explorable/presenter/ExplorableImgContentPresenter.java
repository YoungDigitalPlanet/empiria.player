package eu.ydp.empiria.player.client.module.img.explorable.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.img.explorable.view.ExplorableImgContentView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class ExplorableImgContentPresenter implements ImgContent {

    private boolean zoomInClicked = false;
    private ExplorableImgContentView view;
    private final Timer startZoomTimer;
    private Timer zoomTimer;


    @Inject
    public ExplorableImgContentPresenter(ExplorableImgContentView view) {
        this.view = view;
        startZoomTimer = initializeZoomTimer();
        registerHandlers();
    }

    @Inject
    UserInteractionHandlerFactory handlerFactory;

    private void registerHandlers() {

        EventHandlerProxy handler = createToolboxTouchStartHandler();
        view.registerCommandOnToolbox(handler);

        handler = createZoomInButtonUserDownHandler();
        view.registerZoomInButtonCommands(handler);

        handler = createZoomInOutButtonUserUpHandler();
        view.registerZoomInButtonCommands(handler);
        view.registerZoomOutButtonCommands(handler);

        handler = createZoomOutButtonUserDownHandler();
        view.registerZoomOutButtonCommands(handler);
    }

    private EventHandlerProxy createToolboxTouchStartHandler() {
        Command command = new Command() {
            @Override
            public void execute(NativeEvent event) {
                event.preventDefault();
            }

        };

        return handlerFactory.createUserTouchStartHandler(command);
    }

    private EventHandlerProxy createZoomInButtonUserDownHandler() {
        Command command = new Command() {
            @Override
            public void execute(NativeEvent event) {
                zoomInClicked = true;
                zoomIn();
                startZoomTimer.schedule(500);
                event.preventDefault();
            }

        };

        return handlerFactory.createUserDownHandler(command);
    }

    private EventHandlerProxy createZoomOutButtonUserDownHandler() {
        Command command = new Command() {
            @Override
            public void execute(NativeEvent event) {
                zoomInClicked = false;
                zoomOut();
                startZoomTimer.schedule(500);
                event.preventDefault();
            }

        };

        return handlerFactory.createUserDownHandler(command);
    }


    private EventHandlerProxy createZoomInOutButtonUserUpHandler() {
        Command command = new Command() {
            @Override
            public void execute(NativeEvent event) {
                cancelZoomTimers();
                event.preventDefault();
            }

        };

        return handlerFactory.createUserUpHandler(command);
    }

    private final Timer initializeZoomTimer() {
        zoomTimer = new Timer() {

            @Override
            public void run() {
                zoom();
            }
        };

        return new Timer() {

            @Override
            public void run() {
                zoomTimer.scheduleRepeating(200);
            }
        };
    }

    private void cancelZoomTimers() {
        zoomTimer.cancel();
        startZoomTimer.cancel();
    }

    private void zoomIn() {
        view.zoomIn();
    }

    private void zoomOut() {
        view.zoomOut();
    }

    private void zoom() {
        if (zoomInClicked) {
            view.zoomIn();
        } else {
            view.zoomOut();
        }
    }


    @Override
    public void init(Element element, ModuleSocket moduleSocket) {
        ((ImgContent) view).init(element, moduleSocket);
    }

    @Override
    public Widget asWidget() {
        return ((IsWidget) view).asWidget();
    }
}
