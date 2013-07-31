package eu.ydp.empiria.player.client.module.connection.presenter;

import static eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS;
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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.TestJAXBParser;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionSurfacesManagerFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructureMock;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.style.CssHelper;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

//@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest(value={  CssHelper.class, NodeList.class, Node.class, Style.class,NativeEvent.class })
public class ConnectionModuleViewImplJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {

		@Override
		public void configure(final Binder binder) {
			factory = mock(ConnectionItemsFactory.class);
			ConnectionSurfacesManagerFactory surfacesManagerFactory = mock(ConnectionSurfacesManagerFactory.class);
			binder.bind(ConnectionEventHandler.class).toInstance(connectionEventHandler);
			binder.bind(ConnectionItemsFactory.class).toInstance(factory);
			binder.bind(ConnectionSurfacesManagerFactory.class).toInstance(surfacesManagerFactory);

			binder.bind(CssHelper.class).toInstance(createCssHelperMock());
			binder.bind(ConnectionView.class).toInstance(createConnectionViewMock());

		}

		private ConnectionView createConnectionViewMock() {
			ConnectionView view = mock(ConnectionView.class);
			Widget widget = mock(Widget.class);
			Element element = mock(Element.class);
			doReturn(element).when(widget).getElement();
			doReturn(widget).when(view).asWidget();
			createNodeListMock(element);
			return view;
		}

		private CssHelper createCssHelperMock() {
			CssHelper cssHelper = spy(new CssHelper());
			Style style = mock(Style.class);
			doReturn("none").when(style).getProperty(Mockito.anyString());
			doReturn(style).when(cssHelper).getComputedStyle(Mockito.any(JavaScriptObject.class));
			return cssHelper;
		}

		private void createNodeListMock(final com.google.gwt.user.client.Element element) {
			NodeList<?> nodeList = mock(NodeList.class);
			doReturn(0).when(nodeList).getLength();
			doReturn(nodeList).when(element).getChildNodes();
		}

	}

	private final TestJAXBParser<MatchInteractionBean> jaxb = new TestJAXBParser<MatchInteractionBean>() {
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
		UserAgentChecker.setNativeInterface(UserAgentCheckerNativeInterfaceMock.getNativeInterfaceMock(FIREFOX_WINDOWS));
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
		prepareSurfaceManagerFactory();
		prepareConnectionItems();
		prepareBean();
		prepareModuleSocket();
		prepareConnectionStyleChecker();
		prepareNativeEvent();
		prepareTestInstance();

	}

	private void prepareNativeEvent() {
		event = mock(NativeEvent.class);
	}

	private void prepareConnectionStyleChecker() {
		moduleFactory = injector.getInstance(ConnectionModuleFactory.class);
		ConnectionStyleChecker connectionStyleChacker = injector.getInstance(ConnectionStyleChecker.class);
		injector.getMembersInjector(ConnectionStyleChecker.class).injectMembers(connectionStyleChacker);
	}

	private void prepareModuleSocket() {
		moduleSocket = mock(ModuleSocket.class);
	}

	private void prepareBean() {
		bean = jaxb.parse(ConnectionModuleStructureMock.CONNECTION_XML);
	}

	private void prepareConnectionItems() {
		ConnectionItems connectionItems = new ConnectionItems(mock(InlineBodyGeneratorSocket.class));
		injector.getMembersInjector(ConnectionItems.class).injectMembers(connectionItems);
		this.connectionItems = spy(connectionItems);
		doReturn(this.connectionItems).when(factory).getConnectionItems(Mockito.any(InlineBodyGeneratorSocket.class));

	}

	private void prepareSurfaceManagerFactory() {
		ConnectionSurfacesManagerFactory surfacesManagerFactory = injector.getInstance(ConnectionSurfacesManagerFactory.class);
		when(surfacesManagerFactory.getConnectionSurfacesManager(Mockito.any(HasDimensions.class))).thenAnswer(new Answer<ConnectionSurfacesManager>() {
			@Override
			public ConnectionSurfacesManager answer(final InvocationOnMock invocation) throws Throwable {
				HasDimensions view = (HasDimensions) invocation.getArguments()[0];
				ConnectionSurfacesManager surfacesManager = new ConnectionSurfacesManager(view);
				injector.getMembersInjector(ConnectionSurfacesManager.class).injectMembers(surfacesManager);
				return spy(surfacesManager);
			}
		});
	}

	private void prepareTestInstance() {
		instance = injector.getInstance(ConnectionModuleViewImpl.class);
		instance.setBean(bean);
		instance.setModuleSocket(moduleSocket);
		instance.bindView();
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
		Mockito.verify(surface, times(0)).drawLine(new Point(0, 0), new Point(0, 0));
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
		verify(instance.connectionSurfacesManager).clearConnectionSurface(Mockito.any(ConnectionPairEntry.class));
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
		ConnectionsBetweenItems connectionsBetweenItems = injector.getInstance(ConnectionModuleFactory.class).getConnectionsBetweenItems(mock(IsWidget.class),
				connectionItems);
		ConnectionModuleViewImpl testObject = spy(instance);
		Set<ConnectionItem> connectionItems = testObject.connectionItems.getConnectionItems(null);
		ConnectionItem item = connectionItems.iterator().next();
		doReturn(item).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));

		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, item));
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.when(surface.isPointOnPath(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.verify(connectionEventHandler).fireConnectEvent(Mockito.eq(PairConnectEventTypes.CONNECTED), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyBoolean());

	}

	@Test
	public void connectByClickTest() {
		ConnectionsBetweenItems connectionsBetweenItems = injector.getInstance(ConnectionModuleFactory.class).getConnectionsBetweenItems(mock(IsWidget.class),
				connectionItems);
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
		ConnectionsBetweenItems connectionsBetweenItems = injector.getInstance(ConnectionModuleFactory.class).getConnectionsBetweenItems(mock(IsWidget.class),
				connectionItems);

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
		verify(item2, times(2)).reset();
		verify(item1, times(1)).reset();

	}

	@Test
	public void postConstructTest() {
		ConnectionView view = injector.getInstance(ConnectionView.class);
		verify(view).setDrawFollowTouch(Mockito.eq(true));
	}

	@Test
	public void postConstructAndroidTest() {
		ConnectionView view = injector.getInstance(ConnectionView.class);
		Mockito.reset(view);
		instance.STACK_ANDROID_BROWSER = true;

		instance.postConstruct();
		verify(view).setDrawFollowTouch(Mockito.eq(false));

	}

}
