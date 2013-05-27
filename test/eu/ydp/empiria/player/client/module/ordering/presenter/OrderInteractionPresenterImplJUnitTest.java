package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.ItemClickAction;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionPresenterImplJUnitTest {

	private OrderInteractionPresenterImpl presenter;
	@Mock private OrderInteractionView interactionView;
	@Mock private ItemsMarkingController itemsMarkingController;
	@Mock private OrderingItemsDao orderingItemsDao;
	@Mock private ItemClickController itemClickController;
	@Mock private OrderInteractionModuleFactory orderInteractionModuleFactory;
	@Mock private Response response;
	@Mock private OrderInteractionModuleModel model;
	@Mock private ModuleSocket socket;
	@Mock private OrderingItem item1;
	@Mock private OrderingItem item2;
	@Mock private ItemsResponseOrderController itemsResponseOrderController;
	@Mock private OrderingResetController orderingResetController;
	@Mock private OrderingShowingAnswersController showingAnswersController;
	private OrderInteractionBean bean;

	
	@Before
	public void setUp() throws Exception {
		presenter = new OrderInteractionPresenterImpl(interactionView, itemsMarkingController, orderingItemsDao, itemClickController, orderInteractionModuleFactory,
				itemsResponseOrderController, orderingResetController, showingAnswersController);

		bean = new OrderInteractionBean();

		presenter.setModel(model);
		presenter.setModuleSocket(socket);
		presenter.setBean(bean);
		setUpMocks();
	}

	private void setUpMocks() {
		when(model.getResponse()).thenReturn(response);

		Collection<OrderingItem> items = Lists.newArrayList(item1, item2);
		when(orderingItemsDao.getItems()).thenReturn(items);
	}

	@Test
	public void shouldSetLockedAndUpdateStylesOnView() throws Exception {
		presenter.setLocked(true);

		InOrder inOrder = Mockito.inOrder(item1, item2, interactionView);
		inOrder.verify(item1).setLocked(true);
		inOrder.verify(item2).setLocked(true);
		inOrder.verify(interactionView).setChildStyles(item1);
		inOrder.verify(interactionView).setChildStyles(item2);
		verifyNoMoreInteractionsOnMocks();
	}

	@Test
	public void shouldMarkAnswers() throws Exception {
		MarkAnswersType type = MarkAnswersType.CORRECT;
		MarkAnswersMode mode = MarkAnswersMode.MARK;

		presenter.markAnswers(type, mode);

		InOrder inOrder = Mockito.inOrder(socket, itemsResponseOrderController, itemsMarkingController, interactionView);
		inOrder.verify(itemsMarkingController).markOrUnmarkItemsByType(type, mode);
		inOrder.verify(interactionView).setChildStyles(item1);
		inOrder.verify(interactionView).setChildStyles(item2);

		verifyNoMoreInteractionsOnMocks();
	}

	@Test
	public void shouldBuildViewAndInitializeSubModules() throws Exception {
		// given
		InlineBodyGeneratorSocket bodyGenerator = Mockito.mock(InlineBodyGeneratorSocket.class);
		when(socket.getInlineBodyGeneratorSocket()).thenReturn(bodyGenerator);

		OrderingViewBuilder viewBuilder = Mockito.mock(OrderingViewBuilder.class);
		when(orderInteractionModuleFactory.getViewBuilder(bodyGenerator, bean, interactionView, orderingItemsDao)).thenReturn(viewBuilder);

		List<String> itemsOrder = Lists.newArrayList("item1", "item2");
		when(orderingItemsDao.getItemsOrder()).thenReturn(itemsOrder);

		List<String> responseAnswers = Lists.newArrayList("resp1", "resp2");
		when(itemsResponseOrderController.getResponseAnswersByItemsOrder(itemsOrder)).thenReturn(responseAnswers);

		// when
		presenter.bindView();

		// then
		InOrder inOrder = Mockito.inOrder(interactionView, itemClickController, itemsMarkingController, itemsResponseOrderController, orderInteractionModuleFactory,
				viewBuilder, orderingResetController, showingAnswersController);
		inOrder.verify(interactionView).setClickListener(Mockito.any(OrderItemClickListener.class));
		inOrder.verify(itemClickController).initialize(orderingItemsDao);
		inOrder.verify(itemsMarkingController).initialize(orderingItemsDao, itemsResponseOrderController, model);
		inOrder.verify(itemsResponseOrderController).initialize(orderingItemsDao, model);
		inOrder.verify(showingAnswersController).initialize(orderingItemsDao, itemsResponseOrderController, model);
		inOrder.verify(orderInteractionModuleFactory).getViewBuilder(bodyGenerator, bean, interactionView, orderingItemsDao);
		inOrder.verify(viewBuilder).buildView();
		inOrder.verify(itemsResponseOrderController).updateResponseWithNewOrder(itemsOrder);
		inOrder.verify(orderingResetController).reset();
		inOrder.verify(interactionView).setChildrenOrder(itemsOrder);
	}
	
	@Test
	public void shouldUpdateStyleOfClickedItemWhenWasSelect() throws Exception {
		ItemClickAction clickAction = ItemClickAction.SELECT;
		shouldUpdateStylesOfClickedItemOnAction(clickAction);
	}
	
	@Test
	public void shouldUpdateStyleOfClickedItemWhenWasUnselect() throws Exception {
		ItemClickAction clickAction = ItemClickAction.UNSELECT;
		shouldUpdateStylesOfClickedItemOnAction(clickAction);
	}

	private void shouldUpdateStylesOfClickedItemOnAction(ItemClickAction clickAction) {
		String itemId = "item1";
		when(itemClickController.itemClicked(itemId))
		.thenReturn(clickAction);
		
		when(orderingItemsDao.getItem(itemId))
		.thenReturn(item1);
		
		presenter.itemClicked(itemId);
		
		InOrder inOrder = Mockito.inOrder(itemClickController, interactionView);
		inOrder.verify(itemClickController).itemClicked(itemId);
		inOrder.verify(interactionView).setChildStyles(item1);
		verifyNoMoreInteractionsOnMocks();
	}
	
	@Test
	public void shouldUpdateStylesAndOrderOfAllItemsWhenWasSwap() throws Exception {
		String itemId = "item1";
		when(itemClickController.itemClicked(itemId))
		.thenReturn(ItemClickAction.SWITCH);
		
		List<String> newItemsOrder = Lists.newArrayList("item2", "item1");
		when(orderingItemsDao.getItemsOrder())
			.thenReturn(newItemsOrder);
		
		presenter.itemClicked(itemId);
		
		InOrder inOrder = Mockito.inOrder(itemClickController, interactionView, orderingItemsDao, model, itemsResponseOrderController);
		inOrder.verify(itemClickController).itemClicked(itemId);
		//verify update styles of all items
		inOrder.verify(interactionView).setChildStyles(item1);
		inOrder.verify(interactionView).setChildStyles(item2);
		inOrder.verify(orderingItemsDao).getItemsOrder();
		inOrder.verify(itemsResponseOrderController).updateResponseWithNewOrder(newItemsOrder);
		inOrder.verify(model).onModelChange();
	}
	
	@Test
	public void shouldShowAnswers() throws Exception {
		ShowAnswersType mode = ShowAnswersType.CORRECT;
		List<String> newOrderToShow = Lists.newArrayList("item1", "item2");
		when(showingAnswersController.findNewAnswersOrderToShow(mode))
			.thenReturn(newOrderToShow);
		
		presenter.showAnswers(mode);
		
		verify(showingAnswersController).findNewAnswersOrderToShow(mode);
		verify(interactionView).setChildrenOrder(newOrderToShow);
		verifyNoMoreInteractionsOnMocks();
	}

	private void verifyNoMoreInteractionsOnMocks() {
		verifyNoMoreInteractions(interactionView, itemsMarkingController, itemClickController, orderInteractionModuleFactory, 
				itemsResponseOrderController, orderingResetController, socket, item1, item2, showingAnswersController);
	}
}
