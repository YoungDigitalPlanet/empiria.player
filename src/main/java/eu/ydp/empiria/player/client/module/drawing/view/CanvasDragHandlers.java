package eu.ydp.empiria.player.client.module.drawing.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class CanvasDragHandlers {

    private final EventsBus eventsBus;

    @Inject
    private UserInteractionHandlerFactory handlerFactory;
    @Inject
    private PositionHelper positionHelper;

    @Inject
    public CanvasDragHandlers(EventsBus eventsBus) {
        this.eventsBus = eventsBus;
    }

    public void addHandlersToView(final CanvasPresenter canvasPresenter, final Canvas canvas) {
        handlerFactory.createUserDownHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
                event.preventDefault();
                Point point = positionHelper.getPoint(event, canvas);
                canvasPresenter.mouseDown(point);
            }
        }).apply(canvas);

        handlerFactory.createUserOutHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                canvasPresenter.mouseOut();
            }
        }).apply(canvas);

        handlerFactory.createUserMoveHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                Point point = positionHelper.getPoint(event, canvas);
                canvasPresenter.mouseMove(point);
            }
        }).apply(canvas);

        handlerFactory.createUserUpHandler(new Command() {
            @Override
            public void execute(NativeEvent event) {
                canvasPresenter.mouseUp();
            }
        }).apply(canvas);
    }
}
