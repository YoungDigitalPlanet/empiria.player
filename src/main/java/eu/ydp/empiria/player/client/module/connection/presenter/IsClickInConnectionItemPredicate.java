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

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Predicate;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.util.position.Point;

public final class IsClickInConnectionItemPredicate implements Predicate<ConnectionItem> {
    private final Point clickPoint;

    public IsClickInConnectionItemPredicate(Point clickPoint) {
        this.clickPoint = clickPoint;
    }

    @Override
    public boolean apply(ConnectionItem item) {
        return isItemOnPosition(item, clickPoint);
    }

    private boolean isItemOnPosition(ConnectionItem item, Point point) {
        final int xPos = point.getX();
        final int yPos = point.getY();
        return xPos >= leftBorder(item) && xPos <= rightBorder(item) && yPos >= topBorder(item) && yPos <= bottomBorder(item);
    }

    private int topBorder(ConnectionItem item) {
        return item.getOffsetTop();
    }

    private int leftBorder(ConnectionItem item) {
        return item.getOffsetLeft();
    }

    private int bottomBorder(ConnectionItem item) {
        return topBorder(item) + item.getHeight();
    }

    private int rightBorder(ConnectionItem item) {
        return leftBorder(item) + item.getWidth();
    }

}
