package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemsResponseOrderController {

    private final ItemsOrderByAnswersFinder itemsOrderByAnswersFinder;
    private final OrderingItemsDao orderingItemsDao;
    private final OrderInteractionModuleModel model;

    @Inject
    public ItemsResponseOrderController(ItemsOrderByAnswersFinder itemsOrderByAnswersFinder, @ModuleScoped OrderingItemsDao orderingItemsDao,
                                        @ModuleScoped OrderInteractionModuleModel model) {
        this.itemsOrderByAnswersFinder = itemsOrderByAnswersFinder;
        this.orderingItemsDao = orderingItemsDao;
        this.model = model;
    }

    public List<String> getResponseAnswersByItemsOrder(List<String> itemsOrder) {
        List<String> responseAnswers = Lists.newArrayList();
        for (String itemId : itemsOrder) {
            OrderingItem item = orderingItemsDao.getItem(itemId);
            String answerValue = item.getAnswerValue();
            responseAnswers.add(answerValue);
        }

        return responseAnswers;
    }

    public List<String> getCorrectItemsOrderByAnswers(List<String> answers) {
        Collection<OrderingItem> items = orderingItemsDao.getItems();
        List<String> correctItemsOrder = itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(answers, items);
        return correctItemsOrder;
    }

    public void updateResponseWithNewOrder(List<String> newItemsOrder) {
        List<String> responseAnswers = getResponseAnswersByItemsOrder(newItemsOrder);
        model.getResponse().values = new ArrayList<String>(responseAnswers);
    }

    public List<String> getCurrentItemsOrderByAnswers() {
        List<String> currentAnswers = model.getCurrentAnswers();
        return getCorrectItemsOrderByAnswers(currentAnswers);
    }

}
