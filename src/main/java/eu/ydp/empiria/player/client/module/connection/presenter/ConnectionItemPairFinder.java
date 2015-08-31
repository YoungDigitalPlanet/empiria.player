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
