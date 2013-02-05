package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest(NativeEvent.class)
public class ConnectionsBetweenItemsJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {
		private static final int POS_Y = 40;
		private static final int POS_X = 50;

		@Override
		public void configure(Binder binder) {
			binder.install(new FactoryModuleBuilder().build(ConnectionItemsFactory.class));

		}

		@Provides
		@Singleton
		public PositionHelper getPositionHelper() {
			PositionHelper helper = spy(new PositionHelper());
			Point point = new Point(POS_X, POS_Y);
			doReturn(point).when(helper).getPoint(Mockito.any(NativeEvent.class), Mockito.any(IsWidget.class));
			return helper;
		}
	}

	private ConnectionsBetweenItems instance = null;
	private InlineBodyGeneratorSocket bodyGeneratorSocket;
	private ImmutableList<String> idList;
	private ConnectionItems connectionItems;
	private List<ConnectionItem> items;


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
		Widget widget = prepareWidgetMock();
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
		bodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
		connectionItems = injector.getInstance(ConnectionItemsFactory.class).getConnectionItems(bodyGeneratorSocket);
		prepareTestInstance(widget);
		idList = new ImmutableList.Builder<String>().add("id1").add("id2").add("id3").add("id4").build();
		prepareItems();
	}

	private void prepareTestInstance(Widget widget) {
		ConnectionsBetweenItems connectionsBetweenItems = injector.getInstance(ConnectionModuleFactory.class).getConnectionsBetweenItems(widget, connectionItems);
		injector.getMembersInjector(ConnectionsBetweenItems.class).injectMembers(connectionsBetweenItems);
		instance = connectionsBetweenItems;
	}

	private Widget prepareWidgetMock() {
		IsWidget isWidget = mock(IsWidget.class);
		Element elementMock = mock(Element.class);
		Widget widget = mock(Widget.class);
		doReturn(elementMock).when(widget).getElement();
		doReturn(widget).when(isWidget).asWidget();

		return widget;
	}

	private void prepareItems() {
		items = Lists.newArrayList();
		for (String id : idList) {
			PairChoiceBean bean = mock(PairChoiceBean.class);
			doReturn(id).when(bean).getIdentifier();
			ConnectionItem connectionItem = connectionItems.addItemToLeftColumn(bean);
			doReturn(false).when(connectionItem).isOnPosition(Mockito.anyInt(), Mockito.anyInt());
			items.add(connectionItem);
		}
	}

	@Test
	public void testFindConnectionItem_ItemFound() {
		ConnectionItem connectionItem = items.get(2);
		doReturn(true).when(connectionItem).isOnPosition(Mockito.anyInt(), Mockito.anyInt());

		NativeEvent event = createNativeEventMock();
		ConnectionItem foundConnectionItem = instance.findConnectionItem(event);
		assertEquals(connectionItem, foundConnectionItem);

	}

	private NativeEvent createNativeEventMock() {
		NativeEvent event = mock(NativeEvent.class);
		doNothing().when(event).preventDefault();
		return event;
	}

	@Test
	public void testFindConnectionItem_ItemNotFound() {
		NativeEvent event = createNativeEventMock();
		ConnectionItem foundConnectionItem = instance.findConnectionItem(event);
		assertNull(foundConnectionItem);

	}

}
