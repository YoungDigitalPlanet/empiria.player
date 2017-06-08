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

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;

@Singleton
public class ConnectionItemPairFinder {

    private final class CheckIfItemClickedPredicate implements Predicate<ConnectionItem> {
        private final double x;
        private final double y;

        private CheckIfItemClickedPredicate(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean apply(ConnectionItem item) {
            return isTouchEndOnItem(x, y, item);
        }
    }

    public Optional<ConnectionItem> findConnectionItemForCoordinates(Iterable<ConnectionItem> connectionItems, final int x, final int y) {
        return Iterables.tryFind(connectionItems, new CheckIfItemClickedPredicate(x, y));
    }

    private boolean isTouchEndOnItem(double x, double y, ConnectionItem item) {
        return item.getOffsetLeft() <= x && x <= item.getOffsetLeft() + item.getWidth() && item.getOffsetTop() <= y
                && y <= item.getOffsetTop() + item.getHeight();
    }

}
