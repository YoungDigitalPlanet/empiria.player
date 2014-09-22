package eu.ydp.empiria.player.client.module.ordering.presenter;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionPresenterImplTest {

	private OrderInteractionPresenterImpl testObj;
	@Mock
	private OrderInteractionView interactionView;
	@Mock
	private ItemsMarkingController itemsMarkingController;
	@Mock
	private OrderingItemsDao orderingItemsDao;
	@Mock
	private OrderInteractionModuleFactory orderInteractionModuleFactory;
	@Mock
	private Response response;
	@Mock
	private OrderInteractionModuleModel model;
	@Mock
	private ModuleSocket socket;
	@Mock
	private OrderingItem item1;
	@Mock
	private OrderingItem item2;
	@Mock
	private ItemsResponseOrderController itemsResponseOrderController;
	@Mock
	private OrderingResetController orderingResetController;
	@Mock
	private OrderingShowingAnswersController showingAnswersController;
	@Mock
	private OrderingViewBuilder viewBuilder;
	@Mock
	private DragController dragController;
	private OrderInteractionBean bean;

	@Before
	public void setUp() throws Exception {
		testObj = new OrderInteractionPresenterImpl(itemsMarkingController, itemsResponseOrderController, orderingResetController, showingAnswersController,
				viewBuilder, interactionView, orderingItemsDao, model, dragController);

		bean = new OrderInteractionBean();

		testObj.setModuleSocket(socket);
		testObj.setBean(bean);
		setUpMocks();
	}

	private void setUpMocks() {
		when(model.getResponse()).thenReturn(response);

		Collection<OrderingItem> items = Lists.newArrayList(item1, item2);
		when(orderingItemsDao.getItems()).thenReturn(items);
	}

	@Test
	public void shouldSetLockedAndUpdateStylesOnView() throws Exception {
		testObj.setLocked(true);

		InOrder inOrder = Mockito.inOrder(item1, item2, interactionView);
		inOrder.verify(item1).setLocked(true);
		inOrder.verify(item2).setLocked(true);
		inOrder.verify(interactionView).setChildStyles(item1);
		inOrder.verify(interactionView).setChildStyles(item2);
	}

	@Test
	public void shouldMarkAnswers() throws Exception {
		MarkAnswersType type = MarkAnswersType.CORRECT;
		MarkAnswersMode mode = MarkAnswersMode.MARK;

		testObj.markAnswers(type, mode);

		InOrder inOrder = Mockito.inOrder(socket, itemsResponseOrderController, itemsMarkingController, interactionView);
		inOrder.verify(itemsMarkingController).markOrUnmarkItemsByType(type, mode);
		inOrder.verify(interactionView).setChildStyles(item1);
		inOrder.verify(interactionView).setChildStyles(item2);
	}

	@Test
	public void shouldBuildViewAndInitializeSubModules() throws Exception {
		// given
		InlineBodyGeneratorSocket bodyGenerator = Mockito.mock(InlineBodyGeneratorSocket.class);
		when(socket.getInlineBodyGeneratorSocket()).thenReturn(bodyGenerator);

		List<String> itemsOrder = Lists.newArrayList("item1", "item2");
		when(orderingItemsDao.getItemsOrder()).thenReturn(itemsOrder);

		List<String> responseAnswers = Lists.newArrayList("resp1", "resp2");
		when(itemsResponseOrderController.getResponseAnswersByItemsOrder(itemsOrder)).thenReturn(responseAnswers);

		// when
		testObj.bindView();

		// then
		InOrder inOrder = Mockito.inOrder(interactionView, itemsMarkingController, itemsResponseOrderController, orderInteractionModuleFactory, viewBuilder,
				orderingResetController, showingAnswersController);
		inOrder.verify(viewBuilder).buildView(bean, bodyGenerator);
		inOrder.verify(itemsResponseOrderController).updateResponseWithNewOrder(itemsOrder);
		inOrder.verify(orderingResetController).reset();
		inOrder.verify(interactionView).setChildrenOrder(itemsOrder);
	}

	@Test
	public void shouldShowAnswers() throws Exception {
		ShowAnswersType mode = ShowAnswersType.CORRECT;
		List<String> newOrderToShow = Lists.newArrayList("item1", "item2");
		when(showingAnswersController.findNewAnswersOrderToShow(mode)).thenReturn(newOrderToShow);

		testObj.showAnswers(mode);

		verify(showingAnswersController).findNewAnswersOrderToShow(mode);
		verify(interactionView).setChildrenOrder(newOrderToShow);
	}

	@Test
	public void shouldDisableDragOnLock() {
		// when
		testObj.setLocked(true);

		// then
		verify(dragController).disableDrag();
	}

	@Test
	public void shouldEnableDragOnunlock() {
		// when
		testObj.setLocked(false);

		// then
		verify(dragController).enableDrag();
	}

	@Test
	public void shouldUpdateitemsorder() {
		// given
		List<String> itemsOrder = Lists.newArrayList("a", "b");

		// when
		testObj.updateItemsOrder(itemsOrder);

		// then
		InOrder inOrder = Mockito.inOrder(orderingItemsDao, interactionView, itemsResponseOrderController, model);
		inOrder.verify(orderingItemsDao).setItemsOrder(itemsOrder);
		inOrder.verify(interactionView).setChildStyles(item1);
		inOrder.verify(interactionView).setChildStyles(item2);
		inOrder.verify(interactionView).setChildrenOrder(itemsOrder);
		inOrder.verify(itemsResponseOrderController).updateResponseWithNewOrder(itemsOrder);
		inOrder.verify(model).onModelChange();
	}

	@Test
	public void shouldReturnorientation() {
		// given
		bean.setOrientation(OrderInteractionOrientation.VERTICAL);

		// when
		OrderInteractionOrientation orientation = testObj.getOrientation();

		// then
		assertThat(orientation).isEqualTo(OrderInteractionOrientation.VERTICAL);
	}

	@Test
	public void shouldEnableTestSubmittedMode() {
		// given

		// when
		testObj.enableTestSubmittedMode();

		// then
		verify(interactionView).enableTestSubmittedMode();
	}

	@Test
	public void shouldDisableTestSubmittedMode() {
		// given

		// when
		testObj.disableTestSubmittedMode();

		// then
		verify(interactionView).disableTestSubmittedMode();
	}
}
