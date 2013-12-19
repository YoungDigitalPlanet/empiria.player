package eu.ydp.empiria.player.client.module.ordering.view.items;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;

public class OrderInteractionViewItemsImplJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(OrderInteractionModuleFactory.class).toInstance(moduleFactory);
			binder.bind(ViewItemsSorter.class).toInstance(itemsSorter);
		}
	}

	private final OrderInteractionModuleFactory moduleFactory = mock(OrderInteractionModuleFactory.class);
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
	public void addItem() throws Exception {
		// given
		IsWidget widget = mock(IsWidget.class);

		// when
		OrderInteractionViewItem item = instance.addItem("id", widget);

		// then
		verify(moduleFactory).getOrderInteractionViewItem(Mockito.eq(widget), Mockito.eq("id"));
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

	@Test
	public void getItemsInOrder() throws Exception {
		IsWidget widget = mock(IsWidget.class);
		instance.addItem("id", widget);
		List<String> asList = Arrays.asList("a");
		instance.getItemsInOrder(asList);
		verify(itemsSorter).getItemsInOrder(Mockito.eq(asList), Mockito.anyMapOf(String.class, IsWidget.class));
	}
}
