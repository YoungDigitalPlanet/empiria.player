package eu.ydp.empiria.player.client.module.img.events;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwt.event.shared.EventHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.collections.JsArrayIterable;

import java.util.List;

public class TouchToImageEvent {

    private final Function<Touch, Point> touchToPointTransforemer = new Function<Touch, Point>() {
        @Override
        public Point apply(Touch touch) {
            return new Point(touch.getClientX(), touch.getClientY());
        }
    };

    public TouchOnImageEvent getTouchOnImageEvent(TouchEvent<? extends EventHandler> touchEvent) {
        List<Point> pointsList = getPoints(touchEvent);

        return new TouchOnImageEvent(pointsList);
    }

    private List<Point> getPoints(TouchEvent<? extends EventHandler> touchEvent) {
        JsArray<Touch> touches = touchEvent.getTouches();
        JsArrayIterable<Touch> iterableTouches = JsArrayIterable.create(touches);

        return FluentIterable.from(iterableTouches).transform(touchToPointTransforemer).toList();
    }
}
