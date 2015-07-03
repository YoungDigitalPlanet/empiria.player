package eu.ydp.empiria.player.client.module.connection;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import java.util.Map;

public class ConnectionSurfaceImpl implements ConnectionSurface {
    private final ConnectionSurfaceView view;
    private int offsetTop;
    private int offsetLeft;

    @Inject
    public ConnectionSurfaceImpl(@Assisted HasDimensions dimensions, ConnectionModuleFactory connectionModuleFactory) {
        view = connectionModuleFactory.getConnectionSurfaceView(dimensions);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public void drawLine(Point from, Point to) {
        Point relativeStart = getRelativePoint(from);
        Point relativeEnd = getRelativePoint(to);
        view.drawLine(relativeStart, relativeEnd);
    }

    private Point getRelativePoint(Point point) {
        return new Point(point.getX() - offsetLeft, point.getY() - offsetTop);
    }

    @Override
    public void clear() {
        view.clear();
    }

    @Override
    public boolean isPointOnPath(Point point) {

        return view.isPointOnPath(getRelativePoint(point));
    }

    @Override
    public void applyStyles(Map<String, String> styles) {
        view.applyStyles(styles);
    }

    @Override
    public void removeFromParent() {
        view.removeFromParent();
    }

    @Override
    public int getOffsetLeft() {
        return view.getOffsetLeft();
    }

    @Override
    public void setOffsetLeft(int offsetLeft) {
        this.offsetLeft = offsetLeft;
        view.setOffsetLeft(offsetLeft);
    }

    @Override
    public void setOffsetTop(int offsetTop) {
        this.offsetTop = offsetTop;
        view.setOffsetTop(offsetTop);
    }

}
