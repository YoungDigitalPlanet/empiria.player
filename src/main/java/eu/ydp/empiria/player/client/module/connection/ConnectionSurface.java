package eu.ydp.empiria.player.client.module.connection;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.util.position.Point;

import java.util.Map;

public interface ConnectionSurface extends IsWidget {

    void drawLine(Point from, Point to);

    void clear();

    boolean isPointOnPath(Point point);

    void applyStyles(Map<String, String> styles);

    void removeFromParent();

    int getOffsetLeft();

    void setOffsetLeft(int left);

    void setOffsetTop(int offsetTop);

}
