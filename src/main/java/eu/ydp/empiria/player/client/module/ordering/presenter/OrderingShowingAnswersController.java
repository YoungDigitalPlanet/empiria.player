package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class OrderingShowingAnswersController {

    private final OrderingItemsDao orderingItemsDao;
    private final ItemsResponseOrderController itemsResponseOrderController;
    private final OrderInteractionModuleModel model;

    @Inject
    public OrderingShowingAnswersController(@ModuleScoped ItemsResponseOrderController itemsResponseOrderController,
                                            @ModuleScoped OrderingItemsDao orderingItemsDao, @ModuleScoped OrderInteractionModuleModel model) {
        this.orderingItemsDao = orderingItemsDao;
        this.itemsResponseOrderController = itemsResponseOrderController;
        this.model = model;
    }

    public List<String> findNewAnswersOrderToShow(ShowAnswersType mode) {
        List<String> answersOrder;

        if (mode == ShowAnswersType.CORRECT) {
            List<String> correctAnswers = model.getCorrectAnswers();
            answersOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(correctAnswers);
        } else {
            List<String> currentAnswers = model.getCurrentAnswers();
            answersOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(currentAnswers);
            orderingItemsDao.setItemsOrder(answersOrder);
        }
        return answersOrder;
    }
}
