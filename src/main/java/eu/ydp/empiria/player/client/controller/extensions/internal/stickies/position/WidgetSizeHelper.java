package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class WidgetSizeHelper {

    @Inject
    private IPlayerContainersAccessor accessor;

    public Rectangle getPlayerContainerRectangle() {
        Widget playerContainer = (Widget) accessor.getPlayerContainer();
        return toRectangle(playerContainer);
    }

    public ContainerDimensions getContainerDimensions(IsWidget isWidget) {
        Widget widget = isWidget.asWidget();
        return extractDimensions(widget);
    }

    public ContainerDimensions getPlayerContainerDimensions() {
        Widget playerContainer = (Widget) accessor.getPlayerContainer();
        return extractDimensions(playerContainer);
    }

    private Rectangle toRectangle(Widget widget) {
        return new Rectangle(widget.getAbsoluteLeft(), widget.getAbsoluteTop(), widget.getOffsetWidth(), widget.getOffsetHeight());
    }

    private ContainerDimensions extractDimensions(Widget widget) {
        ContainerDimensions containerDimensions = new ContainerDimensions.Builder().width(widget.getOffsetWidth()).height(widget.getOffsetHeight())
                .absoluteLeft(widget.getAbsoluteLeft()).absoluteTop(widget.getAbsoluteTop()).build();
        return containerDimensions;
    }
}
