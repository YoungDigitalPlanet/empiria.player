package eu.ydp.empiria.player.client.module.connection.presenter;

import static eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock.FIREFOX_UA;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionSurfacesManagerFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructureMock;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest(NativeEvent.class)
public class ConnectionModuleViewImplTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {

		@Override
		public void configure(final Binder binder) {
			factory = mock(ConnectionItemsFactory.class);
			ConnectionSurfacesManagerFactory surfacesManagerFactory = mock(ConnectionSurfacesManagerFactory.class);
			binder.bind(ConnectionEventHandler.class).toInstance(connectionEventHandler);
			binder.bind(ConnectionItemsFactory.class).toInstance(factory);
			binder.bind(ConnectionSurfacesManagerFactory.class).toInstance(surfacesManagerFactory);

		}
	}

	AbstractJAXBTestBase<MatchInteractionBean> jaxb = new AbstractJAXBTestBase<MatchInteractionBean>() {

	};
	private MatchInteractionBean bean;
	private ConnectionModuleViewImpl instance;
	private ModuleSocket moduleSocket;
	private NativeEvent event;
	private ConnectionModuleFactory moduleFactory;
	private ConnectionItems connectionItems;
	private ConnectionItemsFactory factory;
	private final ConnectionEventHandler connectionEventHandler = spy(new ConnectionEventHandler());

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
		UserAgentChecker.setNativeInterface(UserAgentCheckerNativeInterfaceMock.getNativeInterfaceMock(FIREFOX_UA));
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
		ConnectionSurfacesManagerFactory surfacesManagerFactory = injector.getInstance(ConnectionSurfacesManagerFactory.class);
		when(surfacesManagerFactory.getConnectionSurfacesManager(Mockito.any(HasDimensions.class))).thenAnswer(new Answer<ConnectionSurfacesManager>() {
			@Override
			public ConnectionSurfacesManager answer(InvocationOnMock invocation) throws Throwable {
				HasDimensions view = (HasDimensions) invocation.getArguments()[0];
				ConnectionSurfacesManager surfacesManager = new ConnectionSurfacesManager(view);
				injector.getMembersInjector(ConnectionSurfacesManager.class).injectMembers(surfacesManager);
				return spy(surfacesManager);
			}
		});
		ConnectionItems connectionItems = new ConnectionItems(mock(InlineBodyGeneratorSocket.class));
		injector.getMembersInjector(ConnectionItems.class).injectMembers(connectionItems);
		this.connectionItems = spy(connectionItems);
		doReturn(this.connectionItems).when(factory).getConnectionItems(Mockito.any(InlineBodyGeneratorSocket.class));


		jaxb.createUnmarshaller();
		bean = jaxb.createBeanFromXMLString(ConnectionModuleStructureMock.CONNECTION_XML);
		instance = injector.getInstance(ConnectionModuleViewImpl.class);
		moduleFactory = injector.getInstance(ConnectionModuleFactory.class);
		moduleSocket = mock(ModuleSocket.class);
		instance.setBean(bean);
		instance.setModuleSocket(moduleSocket);
		instance.bindView();
		event = mock(NativeEvent.class);

	}

	@Test
	public void connectTest() {
		// test
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0),
				bean.getTargetChoicesIdentifiersSet().get(0), false);
	}

	@Test
	public void wrongConnectTest() {
		// test
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), "---", MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, bean.getSourceChoicesIdentifiersSet().get(0), "---",
				false);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.verify(surface, times(0)).drawLine(0, 0, 0, 0);
	}

	@Test
	public void disconnectTest() {
		// test
		ConnectionModuleViewImpl testObject = spy(instance);
		String source = bean.getSourceChoicesIdentifiersSet().get(0);
		String target = bean.getTargetChoicesIdentifiersSet().get(0);
		testObject.connect(source, target, MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.CONNECTED, source, target, false);
		testObject.disconnect(source, target);
		Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.DISCONNECTED, source, target, false);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		verify(instance.connectionSurfacesManager).clearConnectionSurface(Mockito.any(KeyValue.class));
	}

	@Test
	public void wrongDisconnectTest() {
		// test
		ConnectionModuleViewImpl testObject = spy(instance);
		String sourceIdentifier = bean.getSourceChoicesIdentifiersSet().get(0);
		String targetIdentifier = bean.getTargetChoicesIdentifiersSet().get(0);
		testObject.connect(sourceIdentifier, targetIdentifier, MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.CONNECTED, sourceIdentifier, targetIdentifier, false);
		testObject.disconnect(sourceIdentifier, "---");
		Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, sourceIdentifier, "---", false);
	}

	@Test
	public void resetTest() {
		// test
		instance.reset();
		verify(instance.connectionSurfacesManager).resetAll();
		verify(instance.connectionItems).resetAllItems();
	}

	@Test
	public void pairConnectEventHandlerTest() {
		ConnectionModuleViewImpl testObject = spy(instance);
		PairConnectEventHandler handler = mock(PairConnectEventHandler.class);
		testObject.addPairConnectEventHandler(handler);
		String sourceIdentifier = bean.getSourceChoicesIdentifiersSet().get(0);
		String targetIdentifier = bean.getTargetChoicesIdentifiersSet().get(0);
		testObject.connect(sourceIdentifier, targetIdentifier, MultiplePairModuleConnectType.NORMAL);
		verify(handler).onConnectionEvent(Mockito.any(PairConnectEvent.class));
	}


	@Test
	public void findConnectionTest() {
		ConnectionsBetweenItems connectionsBetweenItems = injector.getInstance(ConnectionModuleFactory.class).getConnectionsBetweenItems(mock(IsWidget.class), connectionItems);
		ConnectionModuleViewImpl testObject = spy(instance);
		Set<ConnectionItem> connectionItems = testObject.connectionItems.getConnectionItems(null);
		ConnectionItem item = connectionItems.iterator().next();
		doReturn(item).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));

		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, item));
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.when(surface.isPointOnPath(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		// verify(event).preventDefault();
		Mockito.verify(connectionEventHandler).fireConnectEvent(Mockito.eq(PairConnectEventTypes.CONNECTED), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyBoolean());

	}

	@Test
	public void connectByClickTest() {
		ConnectionsBetweenItems connectionsBetweenItems = injector.getInstance(ConnectionModuleFactory.class).getConnectionsBetweenItems(mock(IsWidget.class), connectionItems);
		ConnectionModuleViewImpl testObject = spy(instance);
		Set<ConnectionItem> connectionItems = testObject.connectionItems.getConnectionItems(null);
		Iterator<ConnectionItem> iterator = connectionItems.iterator();
		ConnectionItem item1 = iterator.next();
		ConnectionItem item2 = iterator.next();
		doReturn(item1).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
		testObject.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
		doReturn(item2).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
		testObject.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
		verify(testObject).resetConnectionMadeByTouch();
		verify(testObject).connect(eq(item2), eq(item1), eq(MultiplePairModuleConnectType.NORMAL), eq(true));
	}

	@Test
	public void itemHasAnotherConnectionTest() {
		ConnectionsBetweenItems connectionsBetweenItems = injector.getInstance(ConnectionModuleFactory.class).getConnectionsBetweenItems(mock(IsWidget.class), connectionItems);

		ConnectionModuleViewImpl testObject = spy(instance);
		Set<ConnectionItem> connectionItems = testObject.connectionItems.getConnectionItems(null);
		Iterator<ConnectionItem> iterator = connectionItems.iterator();
		ConnectionItem item1 = iterator.next();
		ConnectionItem item2 = iterator.next();
		ConnectionItem item3 = testObject.connectionItems.getConnectionItems(item1).iterator().next();
		doReturn(item1).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
		testObject.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
		doReturn(item2).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
		testObject.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
		doReturn(item1).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
		testObject.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
		verify(item2, times(1)).reset();
		doReturn(item3).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
		testObject.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
		verify(testObject, times(2)).resetConnectionMadeByTouch();
		verify(testObject).connect(eq(item2), eq(item1), eq(MultiplePairModuleConnectType.NORMAL), eq(true));
		verify(testObject).connect(eq(item3), eq(item1), eq(MultiplePairModuleConnectType.NORMAL), eq(true));

		testObject.disconnect(item1.getBean().getIdentifier(), item2.getBean().getIdentifier());
		assertTrue(testObject.connectionSurfacesManager.hasConnections(item1.getBean().getIdentifier()));
		verify(item2, times(1)).reset();
		verify(item1, times(0)).reset();

	}


}
