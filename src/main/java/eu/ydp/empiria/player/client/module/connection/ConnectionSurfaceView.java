package eu.ydp.empiria.player.client.module.connection;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.util.position.Point;

import java.util.Map;

public interface ConnectionSurfaceView extends IsWidget {

    void clear();

    void drawLine(Point relativeStart, Point relativeEnd);

    void applyStyles(Map<String, String> styles);

    void removeFromParent();

    boolean isPointOnPath(Point relativePoint);

    void setOffsetLeft(int offsetLeft);

    int getOffsetLeft();

    void setOffsetTop(int offsetTop);
}
