package eu.ydp.empiria.player.client.module.ordering.view.items;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class OrderInteractionViewItemsImplJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(OrderInteractionModuleFactory.class).toInstance(moduleFactory);
			binder.bind(UserInteractionHandlerFactory.class).toInstance(userInteractionHandlerFactory);
			binder.bind(ViewItemsSorter.class).toInstance(itemsSorter);
		}
	}

	private final OrderInteractionModuleFactory moduleFactory = mock(OrderInteractionModuleFactory.class);
	private final UserInteractionHandlerFactory userInteractionHandlerFactory = mock(UserInteractionHandlerFactory.class);
	private final ViewItemsSorter itemsSorter = mock(ViewItemsSorter.class);
	private OrderInteractionViewItem viewItem;
	private OrderInteractionViewItemsImpl instance;

	@Before
	public void before() {
		setUp(new GuiceModuleConfiguration(), new CustomGinModule());
		viewItem = mock(OrderInteractionViewItem.class);
		when(moduleFactory.getOrderInteractionViewItem(Mockito.any(IsWidget.class), Mockito.anyString())).thenReturn(viewItem);
		instance = injector.getInstance(OrderInteractionViewItemsImpl.class);
	}

	@Test
	public void addItem_itemClickListenerIsNull() throws Exception {
		// given
		IsWidget widget = mock(IsWidget.class);
		executeComand().when(userInteractionHandlerFactory).applyUserClickHandler(any(Command.class), eq(viewItem));

		// when
		OrderInteractionViewItem item = instance.addItem("id", widget);

		// then
		verify(moduleFactory).getOrderInteractionViewItem(Mockito.eq(widget), Mockito.eq("id"));
		verify(userInteractionHandlerFactory).applyUserClickHandler(any(Command.class), Mockito.eq(viewItem));
		assertThat(item).isEqualTo(viewItem);
	}

	@Test
	public void addItem_itemClickListenerIsNotNull() throws Exception {
		// given

		OrderItemClickListener orderItemClickListener = mock(OrderItemClickListener.class);
		instance.setItemClickListener(orderItemClickListener);

		IsWidget widget = mock(IsWidget.class);
		String itemId = "id";

		when(viewItem.getItemId()).thenReturn(itemId);
		executeComand().when(userInteractionHandlerFactory).applyUserClickHandler(any(Command.class), eq(viewItem));

		// when
		OrderInteractionViewItem item = instance.addItem(itemId, widget);

		// then
		verify(moduleFactory).getOrderInteractionViewItem(Mockito.eq(widget), Mockito.eq(itemId));
		verify(userInteractionHandlerFactory).applyUserClickHandler(any(Command.class), Mockito.eq(viewItem));
		verify(viewItem).getItemId();
		verify(orderItemClickListener).itemClicked(itemId);
		assertThat(item).isEqualTo(viewItem);
	}

	@Test
	public void getItem() throws Exception {
		IsWidget widget = mock(IsWidget.class);
		OrderInteractionViewItem addItem = instance.addItem("id", widget);
		OrderInteractionViewItem getItem = instance.getItem("id");

		assertThat(addItem).isEqualTo(getItem);
		assertThat(instance.getItem("")).isNull();

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getItemsInOrder() throws Exception {
		IsWidget widget = mock(IsWidget.class);
		instance.addItem("id", widget);
		List<String> asList = Arrays.asList("a");
		instance.getItemsInOrder(asList);
		verify(itemsSorter).getItemsInOrder(Mockito.eq(asList), Mockito.anyMap());
	}

	public static <T> Stubber executeComand() {
		return Mockito.doAnswer(new Answer<T>() {
			@Override
			public T answer(InvocationOnMock invocationOnMock) throws Throwable {
				final Object[] args = invocationOnMock.getArguments();
				((Command) args[0]).execute(null);
				return null;
			}
		});
	}

}
