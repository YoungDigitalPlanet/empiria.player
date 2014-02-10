package eu.ydp.empiria.player.client.module.ordering.presenter;

import static org.junit.Assert.assertEquals;
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
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;

@RunWith(MockitoJUnitRunner.class)
public class ItemsResponseOrderControllerJUnitTest {

	private ItemsResponseOrderController itemsResponseOrderController;
	private OrderingItemsDao orderingItemsDao;
	private OrderInteractionModuleModel model;
	@Mock
	private ItemsOrderByAnswersFinder itemsOrderByAnswersFinder;
	private Response response;

	@Before
	public void setUp() throws Exception {
		orderingItemsDao = new OrderingItemsDao();
		response = new ResponseBuilder().build();
		model = new OrderInteractionModuleModel(response);
		itemsResponseOrderController = new ItemsResponseOrderController(itemsOrderByAnswersFinder, orderingItemsDao, model);
	}

	@Test
	public void shouldMapResponseAnswersByItemsOrder() throws Exception {
		List<String> itemsOrder = Lists.newArrayList("3", "1", "2");
		addItem("1", "responseValue1");
		addItem("2", "responseValue2");
		addItem("3", "responseValue3");

		List<String> responseAnswersByItemsOrder = itemsResponseOrderController.getResponseAnswersByItemsOrder(itemsOrder);

		List<String> expectedResponseAnswersOrder = Lists.newArrayList("responseValue3", "responseValue1", "responseValue2");
		assertEquals(expectedResponseAnswersOrder, responseAnswersByItemsOrder);

		Mockito.verifyNoMoreInteractions(itemsOrderByAnswersFinder);
	}

	@Test
	public void shouldMapItemsOrderFromResponseAnswers() throws Exception {
		addItem("1", "responseValue1");
		addItem("2", "responseValue2");

		List<String> currentAnswers = Lists.newArrayList("responseValue1");
		List<String> itemsOrder = Lists.newArrayList("2", "1");
		when(itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(currentAnswers, orderingItemsDao.getItems())).thenReturn(itemsOrder);

		List<String> result = itemsResponseOrderController.getCorrectItemsOrderByAnswers(currentAnswers);

		assertEquals(itemsOrder, result);
	}

	private void addItem(String id, String answerValue) {
		OrderingItem orderingItem = new OrderingItem(id, answerValue);
		orderingItemsDao.addItem(orderingItem);
	}

	@Test
	public void shouldUpdateResponseWithNewOrder() throws Exception {
		List<String> itemsOrder = Lists.newArrayList("3", "1", "2");
		addItem("1", "responseValue1");
		addItem("2", "responseValue2");
		addItem("3", "responseValue3");

		itemsResponseOrderController.updateResponseWithNewOrder(itemsOrder);

		List<String> expectedResponseAnswersOrder = Lists.newArrayList("responseValue3", "responseValue1", "responseValue2");
		assertEquals(expectedResponseAnswersOrder, response.values);
	}

	@Test
	public void shouldReturnCurrentItemsOrderByAnswers() throws Exception {
		addItem("1", "responseValue1");
		addItem("2", "responseValue2");

		List<String> currentAnswers = Lists.newArrayList("currentAnswers");
		response.values = currentAnswers;

		List<String> itemsOrder = Lists.newArrayList("2", "1");
		when(itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(currentAnswers, orderingItemsDao.getItems())).thenReturn(itemsOrder);

		List<String> result = itemsResponseOrderController.getCurrentItemsOrderByAnswers();

		assertEquals(itemsOrder, result);
	}
}
