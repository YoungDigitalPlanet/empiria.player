package eu.ydp.empiria.player.client.module.ordering.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemStyles;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItems;

@SuppressWarnings("PMD")
public class OrderInteractionViewImplJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit{


	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(OrderInteractionViewWidget.class).toInstance(viewWidget);
			binder.bind(OrderInteractionViewItems.class).toInstance(viewItems);
			binder.bind(OrderInteractionViewItemStyles.class).toInstance(itemStyles);
		}
	}

	private final OrderInteractionViewWidget viewWidget = mock(OrderInteractionViewWidget.class);
	private final OrderInteractionViewItems viewItems = mock(OrderInteractionViewItems.class);
	private final OrderInteractionViewItemStyles itemStyles = mock(OrderInteractionViewItemStyles.class);
	private final InlineBodyGeneratorSocket bodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
	private final OrderInteractionViewItem viewItem = mock(OrderInteractionViewItem.class);
	private OrderInteractionViewImpl instance;
	private Widget widget;


	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before() {
		GuiceModuleConfiguration moduleConfig = new GuiceModuleConfiguration();
		setUp(moduleConfig, new CustomGinModule());
		instance = injector.getInstance(OrderInteractionViewImpl.class);
		widget = mock(Widget.class);
		when(bodyGeneratorSocket.generateInlineWidget(Mockito.any(Node.class))).thenReturn(widget);
		when(viewItems.addItem(Mockito.anyString(),Mockito.any(Widget.class))).thenReturn(viewItem);
		when(viewItems.getItem(Mockito.anyString())).thenReturn(viewItem);
	}

	@Test
	public void createItem() throws Exception {
		XMLContent xmlContent = mock(XMLContent.class);
		OrderingItem orderingItem = new OrderingItem("id","ans");
		instance.createItem(orderingItem, xmlContent, bodyGeneratorSocket);
		verify(viewWidget).add(Mockito.eq(viewItem));
		verify(viewItems).addItem(Mockito.eq("id"), Mockito.eq(widget));
	}

	@Test
	public void setChildrenOrder() throws Exception {
		final List<String> order = fillInstance();

		instance.setChildrenOrder(order);
		verify(viewItems).getItemsInOrder(Mockito.eq(order));
		verify(viewWidget).putItemsOnView(Mockito.anyListOf(IsWidget.class));
	}

	private List<String> fillInstance() {
		final XMLContent xmlContent = mock(XMLContent.class);
		final List<String> order = Arrays.asList("id","id2","id3");
		for(final String orderItem: order){
			final OrderingItem orderingItem = new OrderingItem(orderItem,"ans");
			instance.createItem(orderingItem, xmlContent, bodyGeneratorSocket);
		}
		return order;
	}

	@Test
	public void setClickListener() throws Exception {
		OrderItemClickListener clickListener = mock(OrderItemClickListener.class);
		instance.setClickListener(clickListener);
		verify(viewItems).setItemClickListener(Mockito.eq(clickListener));
	}

	@Test
	public void setChildStyles() throws Exception {
		fillInstance();
		OrderingItem item = new OrderingItem("id","ans");
		instance.setChildStyles(item);
		verify(viewItems).getItem(Mockito.eq("id"));
		Mockito.verify(itemStyles).applyStylesOnWidget(Mockito.eq(item), Mockito.eq(viewItem));
	}

}
