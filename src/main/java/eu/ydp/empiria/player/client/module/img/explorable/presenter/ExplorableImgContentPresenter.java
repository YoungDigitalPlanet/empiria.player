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
import eu.ydp.empiria.player.client.module.img.explorable.view.ImageProperties;
import eu.ydp.empiria.player.client.module.img.explorable.view.StyleParser;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class ExplorableImgContentPresenter implements ImgContent {

    private ExplorableImgContentView view;
    private final Timer zoomInTicker;
    private final Timer zoomOutTicker;
    private StyleParser styleParser;


    @Inject
    public ExplorableImgContentPresenter(ExplorableImgContentView view, UserInteractionHandlerFactory handlerFactory, StyleParser styleParser) {
        this.view = view;
        this.handlerFactory = handlerFactory;
        this.styleParser = styleParser;
        zoomInTicker = initializeZoomInTimer();
        zoomOutTicker = initializeZoomOutTimer();
        registerHandlers();
    }

    UserInteractionHandlerFactory handlerFactory;

    private void registerHandlers() {

        EventHandlerProxy handler = createToolboxTouchStartHandler();
        view.registerCommandOnToolbox(handler);

        handler = createZoomInButtonUserDownHandler();
        view.registerZoomInButtonCommands(handler);

        handler = createZoomInButtonUserUpHandler();
        view.registerZoomInButtonCommands(handler);

        handler = createZoomOutButtonUserUpHandler();
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
                zoomIn();
                zoomInTicker.scheduleRepeating(200);
                event.preventDefault();
            }

        };

        return handlerFactory.createUserDownHandler(command);
    }

    private EventHandlerProxy createZoomOutButtonUserDownHandler() {
        Command command = new Command() {
            @Override
            public void execute(NativeEvent event) {
                zoomOut();
                zoomOutTicker.scheduleRepeating(200);
                event.preventDefault();
            }

        };

        return handlerFactory.createUserDownHandler(command);
    }


    private EventHandlerProxy createZoomInButtonUserUpHandler() {
        Command command = new Command() {
            @Override
            public void execute(NativeEvent event) {
                cancelzoomInTicker();
                event.preventDefault();
            }

        };

        return handlerFactory.createUserUpHandler(command);
    }

    private EventHandlerProxy createZoomOutButtonUserUpHandler() {
        Command command = new Command() {
            @Override
            public void execute(NativeEvent event) {
                cancelzoomOutTicker();
                event.preventDefault();
            }

        };

        return handlerFactory.createUserUpHandler(command);
    }

    private Timer initializeZoomInTimer() {
        Timer zoomTimer = new Timer() {

            @Override
            public void run() {
                zoomIn();
            }
        };
        return zoomTimer;
    }

    private Timer initializeZoomOutTimer() {
        Timer zoomTimer = new Timer() {

            @Override
            public void run() {
                zoomOut();
            }
        };

        return zoomTimer;
    }

    private void cancelzoomInTicker() {
        zoomInTicker.cancel();
    }

    private void cancelzoomOutTicker() {
        zoomOutTicker.cancel();
    }

    private void zoomIn() {
        view.zoomIn();
    }

    private void zoomOut() {
        view.zoomOut();
    }

    @Override
    public void init(Element element, ModuleSocket moduleSocket) {
        ImageProperties imageProperties = styleParser.parseStyles(element);
        view.init(element, moduleSocket, imageProperties);
    }

    @Override
    public Widget asWidget() {
        return ((IsWidget) view).asWidget();
    }
}
