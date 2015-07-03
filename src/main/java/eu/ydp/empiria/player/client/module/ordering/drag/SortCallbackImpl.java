package eu.ydp.empiria.player.client.module.ordering.drag;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedLazyProvider;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class SortCallbackImpl implements SortCallback {

    @Inject
    private ModuleScopedLazyProvider<OrderInteractionPresenter> presenterProvider;
    @Inject
    private TouchController touchController;
    @Inject
    @ModuleScoped
    private OrderingItemsDao orderingItemsDao;

    @Override
    public void sortStoped(int from, int to) {
        List<String> itemsInOrder = orderingItemsDao.getItemsOrder();

        String movedElement = itemsInOrder.remove(from);
        itemsInOrder.add(to, movedElement);

        presenterProvider.get().updateItemsOrder(itemsInOrder);
    }

    @Override
    public void setSwypeLock(boolean lock) {
        touchController.setSwypeLock(lock);
    }
}
