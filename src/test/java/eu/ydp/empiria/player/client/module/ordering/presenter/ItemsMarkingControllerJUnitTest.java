package eu.ydp.empiria.player.client.module.ordering.presenter;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

@RunWith(MockitoJUnitRunner.class)
public class ItemsMarkingControllerJUnitTest {

	private ItemsMarkingController itemsMarkingController;
	private OrderingItemsDao orderingItemsDao;
	@Mock
	private ItemsResponseOrderController responseOrderController;
	@Mock
	private OrderInteractionModuleModel model;
	@Mock
	private ResponseSocket responseSocket;
	private OrderingItem correctItem1;
	private OrderingItem wrongItem;
	private OrderingItem correctItem2;
	private final List<Boolean> answersEvaluation = Lists.newArrayList(true, false, true);

	@Before
	public void setUp() throws Exception {
		orderingItemsDao = new OrderingItemsDao();
		itemsMarkingController = new ItemsMarkingController(responseOrderController, responseSocket, model, orderingItemsDao);

		correctItem1 = createItem("correctItem1");
		wrongItem = createItem("wrongItem");
		correctItem2 = createItem("correctItem2");

		Response response = Mockito.mock(Response.class);
		when(model.getResponse()).thenReturn(response);

		when(responseSocket.evaluateResponse(response)).thenReturn(answersEvaluation);

		List<String> currentItemsOrder = Lists.newArrayList("correctItem1", "wrongItem", "correctItem2");
		when(responseOrderController.getCurrentItemsOrderByAnswers()).thenReturn(currentItemsOrder);
	}

	@Test
	public void shouldUnmarkCorrectItems() throws Exception {
		// given
		List<OrderingItem> orderingCorrectItems = Lists.newArrayList(correctItem1, correctItem2);
		MarkAnswersType type = MarkAnswersType.CORRECT;
		MarkAnswersMode mode = MarkAnswersMode.UNMARK;

		// when
		itemsMarkingController.markOrUnmarkItemsByType(type, mode);

		// then
		assertItemsUnmarked(orderingCorrectItems);
	}

	private void assertItemsUnmarked(List<OrderingItem> orderingItems) {
		for (OrderingItem orderingItem : orderingItems) {
			assertThat(orderingItem.getAnswerType()).isEqualTo(UserAnswerType.NONE);
		}
	}

	@Test
	public void shouldMarkCorrectItems() throws Exception {
		shouldMarkItems(UserAnswerType.CORRECT, MarkAnswersType.CORRECT, Lists.newArrayList(correctItem1, correctItem2));
	}

	@Test
	public void shouldMarkWrongItems() throws Exception {
		shouldMarkItems(UserAnswerType.WRONG, MarkAnswersType.WRONG, Lists.newArrayList(wrongItem));
	}

	private void shouldMarkItems(UserAnswerType answerType, MarkAnswersType markAnswersType, List<OrderingItem> itemsWhichShouldBeMarked) {
		// given
		MarkAnswersType type = markAnswersType;
		MarkAnswersMode mode = MarkAnswersMode.MARK;

		// when
		itemsMarkingController.markOrUnmarkItemsByType(type, mode);

		// then
		assertItemsMarked(itemsWhichShouldBeMarked, answerType);
	}

	private void assertItemsMarked(List<OrderingItem> orderingItems, UserAnswerType userAnswerType) {
		for (OrderingItem orderingItem : orderingItems) {
			assertThat(orderingItem.getAnswerType()).isEqualTo(userAnswerType);
		}
	}

	private OrderingItem createItem(String id) {
		OrderingItem orderingItem = new OrderingItem(id, "whatever");
		orderingItemsDao.addItem(orderingItem);
		return orderingItem;
	}

}
