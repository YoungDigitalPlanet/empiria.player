package eu.ydp.empiria.player.client.util.position;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.user.client.ui.IsWidget;

public class PositionHelper {

    public int getYPositionRelativeToTarget(NativeEvent event, Element target) {
        Touch touch = getTouch(event);
        float positionY = 0;// hack dont change to int
        if (touch == null) {
            positionY = getRelativeY(event, target);
        } else {
            positionY = touch.getRelativeY(target);
        }
        return (int) positionY;
    }

    private int getRelativeY(NativeEvent event, Element target) {
        return event.getClientY() - getAbsoluteTop(target) + target.getScrollTop() + target.getOwnerDocument().getScrollTop();
    }

    private int getAbsoluteTop(Element target) {

        int offsetTop = target.getOffsetTop();

        if (offsetTop != 0) {
            return offsetTop + getAbsoluteTop(target.getOffsetParent());
        }

        return target.getAbsoluteTop();
    }

    /**
     * zwraca relatywna pozycje X zdarzenia do elementu target
     *
     * @param event
     * @param target
     * @return
     */
    public int getXPositionRelativeToTarget(NativeEvent event, Element target) {
        Touch touch = getTouch(event);
        float positionX = 0; // hack dont change to int
        if (touch == null) {
            positionX = getRelativeX(event, target);
        } else {
            positionX = touch.getRelativeX(target);
        }
        return (int) positionX;
    }

    private int getRelativeX(NativeEvent event, Element target) {
        return event.getClientX() - target.getAbsoluteLeft() + target.getScrollLeft() + target.getOwnerDocument().getScrollLeft();
    }

    private Touch getTouch(NativeEvent event) {
        JsArray<Touch> touches = event.getChangedTouches();
        Touch touch = null;
        if (touches != null && touches.length() == 1) {
            touch = touches.get(0);
        }
        return touch;
    }

    public Point getPoint(NativeEvent event, Element target) {
        return new Point(getXPositionRelativeToTarget(event, target), getYPositionRelativeToTarget(event, target));
    }

    public Point getPoint(NativeEvent event, IsWidget widget) {
        com.google.gwt.user.client.Element target = widget.asWidget().getElement();
        return new Point(getXPositionRelativeToTarget(event, target), getYPositionRelativeToTarget(event, target));
    }

}
