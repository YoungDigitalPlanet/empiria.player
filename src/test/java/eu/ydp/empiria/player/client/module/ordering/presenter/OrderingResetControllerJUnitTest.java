package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderingResetControllerJUnitTest {

    @InjectMocks
    private OrderingResetController controller;
    @Mock
    private OrderingAnswersShuffler orderingAnswersShuffler;
    @Mock
    private OrderingItemsDao orderingItemsDao;
    @Mock
    private ItemsResponseOrderController itemsResponseOrderController;
    @Mock
    private OrderInteractionModuleModel model;

    @Test
    public void shouldResetAnswersToNewRandomOnes() throws Exception {
        List<String> currentAnswers = Lists.newArrayList("currAnswers");
        when(model.getCurrentAnswers()).thenReturn(currentAnswers);

        List<String> correctAnswers = Lists.newArrayList("correctAnswers");
        when(model.getCorrectAnswers()).thenReturn(correctAnswers);

        List<String> newAnswersOrder = Lists.newArrayList("new Answers Order");
        when(orderingAnswersShuffler.shuffleAnswers(currentAnswers, correctAnswers)).thenReturn(newAnswersOrder);

        List<String> newItemsOrder = Lists.newArrayList("newItemsOrder");
        when(itemsResponseOrderController.getCorrectItemsOrderByAnswers(newAnswersOrder)).thenReturn(newItemsOrder);

        controller.reset();

        InOrder inOrder = Mockito.inOrder(orderingItemsDao, itemsResponseOrderController, orderingAnswersShuffler);
        inOrder.verify(orderingAnswersShuffler).shuffleAnswers(currentAnswers, correctAnswers);
        inOrder.verify(itemsResponseOrderController).getCorrectItemsOrderByAnswers(newAnswersOrder);
        inOrder.verify(orderingItemsDao).setItemsOrder(newItemsOrder);
        inOrder.verify(itemsResponseOrderController).updateResponseWithNewOrder(newItemsOrder);

        Mockito.verifyNoMoreInteractions(orderingAnswersShuffler, orderingItemsDao, itemsResponseOrderController);
    }

}
