package eu.ydp.empiria.player.client.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import eu.ydp.empiria.player.client.module.listener.ITouchEventsListener;

public class TouchablePanel extends AbsolutePanel {

    public TouchablePanel(ITouchEventsListener tel) {
        super();
        touchEventsListener = tel;
        registerTouchEvents(this.getElement());
    }

    private ITouchEventsListener touchEventsListener;

    private native void registerTouchEvents(Element element)/*-{
        var instance = this;
        element.ontouchstart = function (e) {
            e.preventDefault();
            if (e.touches.length == 1) {
                var touch = e.touches[0];
                instance.@eu.ydp.empiria.player.client.components.TouchablePanel::processOnTouchStartEvent(II)(touch.pageX, touch.pageY);
            }
        }

        element.ontouchmove = function (e) {
            e.preventDefault();
            if (e.touches.length == 1) {
                var touch = e.touches[0];
                instance.@eu.ydp.empiria.player.client.components.TouchablePanel::processOnTouchMoveEvent(II)(touch.pageX, touch.pageY);
            }
        }

        element.ontouchend = function (e) {
            e.preventDefault();
            instance.@eu.ydp.empiria.player.client.components.TouchablePanel::processOnTouchEndEvent()();
        }

    }-*/;

    private void processOnTouchStartEvent(int x, int y) {
        touchEventsListener.onTouchStart(this.getElement(), x, y);
    }

    private void processOnTouchMoveEvent(int x, int y) {
        touchEventsListener.onTouchMove(this.getElement(), x, y);
    }

    private void processOnTouchEndEvent() {
        touchEventsListener.onTouchEnd(this.getElement());
    }

}
