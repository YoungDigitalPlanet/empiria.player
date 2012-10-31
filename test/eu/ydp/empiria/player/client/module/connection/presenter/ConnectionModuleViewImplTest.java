package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructureMock;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;

@SuppressWarnings("PMD")
public class ConnectionModuleViewImplTest extends AbstractTestBase {
	AbstractJAXBTestBase<MatchInteractionBean> jaxb = new AbstractJAXBTestBase<MatchInteractionBean>() {

	};
	private MatchInteractionBean bean;
	private ConnectionModuleViewImpl instance;
	private ModuleSocket moduleSocket;
	private NativeEvent event;
	private ConnectionModuleFactory moduleFactory;
	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before(){
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
	public void connectTest(){
		//test
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0),false);
	}

	@Test
	public void wrongConnectTest(){
		//test
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), "---", MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, bean.getSourceChoicesIdentifiersSet().get(0), "---",false);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.verify(surface,times(0)).drawLine(0, 0, 0, 0);
	}


	@Test
	public void disconnectTest(){
		//test
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0),false);
		testObject.disconnect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0));
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.DISCONNECTED, bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0),false);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.verify(surface).clear();
		Mockito.verify(surface).removeFromParent();
	}

	@Test
	public void wrongDisconnectTest(){
		//test
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0),false);
		testObject.disconnect(bean.getSourceChoicesIdentifiersSet().get(0), "---");
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, bean.getSourceChoicesIdentifiersSet().get(0), "---",false);
	}

	@Test
	public void resetTest(){
		//test
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0),false);
		testObject.reset();
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.verify(surface).removeFromParent();
		Mockito.reset(surface);
		testObject.reset();
		verify(surface,times(0)).clear();
		verify(surface,times(0)).removeFromParent();
	}

	@Test
	public void pairConnectEventHandlerTest(){
		ConnectionModuleViewImpl testObject = spy(instance);
		PairConnectEventHandler handler = mock(PairConnectEventHandler.class);
		testObject.addPairConnectEventHandler(handler);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		verify(handler).onConnectionEvent(Mockito.any(PairConnectEvent.class));
	}

	@Test
	public void duplicateConnectionTest(){
		ConnectionModuleViewImpl testObject = spy(instance);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(testObject).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0),false);
		Mockito.reset(testObject);
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		Mockito.verify(testObject,times(0)).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0),false);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.verify(surface).removeFromParent();
	}

	@Test
	public void clearSurfaceTest(){
		ConnectionModuleViewImpl testObject = spy(instance);
		Set<ConnectionItem> connectionItems = testObject.getConnectionItems(null);
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, connectionItems.iterator().next()));
		testObject.setLocked(true);
		testObject.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.verify(surface).drawLine(0, 0, 0, 0);
		Mockito.verify(surface).clear();
	}

	@Test
	public void findConnectionTest(){
		ConnectionModuleViewImpl testObject = spy(instance);
		Set<ConnectionItem> connectionItems = testObject.getConnectionItems(null);
		testObject.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, connectionItems.iterator().next()));
		testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
		ConnectionSurface surface = moduleFactory.getConnectionSurface(0, 0);
		Mockito.when(surface.isPointOnPath(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
//		verify(event).preventDefault();
		Mockito.verify(testObject).fireConnectEvent(Mockito.eq(PairConnectEventTypes.CONNECTED), Mockito.anyString(), Mockito.anyString(),Mockito.anyBoolean());

	}













}
