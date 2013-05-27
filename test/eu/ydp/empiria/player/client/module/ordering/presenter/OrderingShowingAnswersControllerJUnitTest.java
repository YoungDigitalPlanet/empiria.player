package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;

@RunWith(MockitoJUnitRunner.class)
public class OrderingShowingAnswersControllerJUnitTest {

	private OrderingShowingAnswersController controller;
	@Mock private OrderingItemsDao orderingItemsDao;
	@Mock private ItemsResponseOrderController itemsResponseOrderController;
	@Mock private OrderInteractionModuleModel model;
	
	@Before
	public void setUp() throws Exception {
		controller = new OrderingShowingAnswersController();
		controller.initialize(orderingItemsDao, itemsResponseOrderController, model);
	}

	@Test
	public void shouldShowCorrectAnswers() throws Exception {
		//given
		ShowAnswersType mode = ShowAnswersType.CORRECT;
		
		List<String> correctAnswers = Lists.newArrayList("correctAnswers");
		when(model.getCorrectAnswers())
			.thenReturn(correctAnswers);
		
		List<String> newAnswersOrder = Lists.newArrayList("newAnswersOrder");
		when(itemsResponseOrderController.getCorrectItemsOrderByAnswers(correctAnswers))
			.thenReturn(newAnswersOrder );
		
		//when
		List<String> result = controller.findNewAnswersOrderToShow(mode);
		
		//then
		assertEquals(newAnswersOrder, result);
	}
	
	@Test
	public void shouldShowAnswersSpecyfiedByCurrentAnswers() throws Exception {
		//given
		ShowAnswersType mode = ShowAnswersType.USER;
		
		List<String> currentAnswers = Lists.newArrayList("correctAnswers");
		when(model.getCurrentAnswers())
			.thenReturn(currentAnswers);
		
		List<String> newAnswersOrder = Lists.newArrayList("newAnswersOrder");
		when(itemsResponseOrderController.getCorrectItemsOrderByAnswers(currentAnswers))
			.thenReturn(newAnswersOrder );

		//when
		List<String> result = controller.findNewAnswersOrderToShow(mode);
		
		//then
		assertEquals(newAnswersOrder, result);
		verify(orderingItemsDao).setItemsOrder(newAnswersOrder);
	}
}
