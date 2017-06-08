/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
