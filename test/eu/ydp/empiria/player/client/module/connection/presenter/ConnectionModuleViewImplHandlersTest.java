package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionItemFluentMockBuilder;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionModuleViewImplHandlersTest {

	@InjectMocks
	private ConnectionModuleViewImplHandlers testObj;

	@Mock
	private ConnectionModuleViewImpl view;

	@Mock
	private ConnectionPairEntry<ConnectionItem, ConnectionItem> connectionPairEntry;

	@Mock
	private ConnectionItem sourceConnectionItem;

	@Mock
	private PairChoiceBean bean;

	@Mock
	private ConnectionItemPairFinder pairFinder;

	@Mock
	private ConnectionItems connectionItems;

	@Mock
	private UserAgentCheckerWrapper userAgent;

	@Mock
	private ConnectionPairEntry<Double, Double> lastPoint;
	
	@Before
	public void setUp() {
		testObj.setView(view);

		when(sourceConnectionItem.getBean()).thenReturn(bean);
		when(connectionPairEntry.getSource()).thenReturn(sourceConnectionItem);
		when(view.getConnectionItemPair()).thenReturn(connectionPairEntry);
		when(view.getConnectionItems()).thenReturn(connectionItems);
		when(view.getLastPoint()).thenReturn(lastPoint);
	}

	@Test
	public void testOnConnectionCancel_noSource() {
		// given
		when(connectionPairEntry.getSource()).thenReturn(null);

		// when
		testObj.onConnectionMoveCancel();

		// then
		verify(view, times(0)).resetIfNotConnected(anyString());
		verify(view, times(0)).resetTouchConnections();
		verify(view, times(0)).clearSurface(any(ConnectionItem.class));
	}

	@Test
	public void testOnConnectionCancel_hasSource() {
		// given
		final String ID = "id";
		when(bean.getIdentifier()).thenReturn(ID);

		// when
		testObj.onConnectionMoveCancel();

		// then
		verify(view).resetIfNotConnected(ID);
		verify(view).resetTouchConnections();
		verify(view).clearSurface(sourceConnectionItem);
	}

	@Test
	public void testOnConnectionMove_elementNotFound_mayConnect() {
		// given
		final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
		when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
				Optional.<ConnectionItem> absent());
		when(view.isLocked()).thenReturn(false);

		// when
		testObj.onConnectionMoveEnd(event);

		// then
		verify(view, times(0)).drawLineFromSource(anyInt(), anyInt());
		verify(view, times(0)).connect(eq(sourceConnectionItem), any(ConnectionItem.class), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
		verify(view).clearSurface(sourceConnectionItem);
	}

	@Test
	public void testOnConnectionMove_elementNotFound_mayNotConnect() {
		// given
		final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
		when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
				Optional.<ConnectionItem> absent());
		when(view.isLocked()).thenReturn(false);
		final ConnectionItem targetConnectionItem = ConnectionItemFluentMockBuilder.newConnectionItem().build();
		when(connectionPairEntry.getTarget()).thenReturn(targetConnectionItem);

		// when
		testObj.onConnectionMoveEnd(event);

		// then
		verify(view, times(0)).drawLineFromSource(anyInt(), anyInt());
		verify(view, times(1)).connect(eq(sourceConnectionItem), eq(targetConnectionItem), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
		verify(view).clearSurface(sourceConnectionItem);
	}
	
	@Test
	public void testOnConnectionMove_elementFound() {
		// given
		final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
		final ConnectionItem targetConnectionItem = ConnectionItemFluentMockBuilder.newConnectionItem().build();
		when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
				Optional.of(targetConnectionItem));
		when(view.isLocked()).thenReturn(false);
		
		// when
		testObj.onConnectionMoveEnd(event);
		
		// then
		verify(view, times(1)).drawLineFromSource(anyInt(), anyInt());
		verify(view, times(0)).connect(eq(sourceConnectionItem), eq(targetConnectionItem), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
		verify(view).clearSurface(sourceConnectionItem);
	}
	
	@Test
	public void testOnConnectionMoveEnd_lockedView() {
		// given
		final ConnectionMoveEvent connectionMoveEvent = mock(ConnectionMoveEvent.class);
		when(view.isLocked()).thenReturn(true);
		
		
		// when
		testObj.onConnectionMove(connectionMoveEvent);
		
		// then
		verify(connectionMoveEvent, times(0)).preventDefault();
		verify(view, times(0)).drawLineFromSource(anyInt(), anyInt());
	}
	
	@Test
	public void testOnConnectionMoveEnd_unlockedView_largeX() {
		// given
		final ConnectionMoveEvent connectionMoveEvent = mock(ConnectionMoveEvent.class);
		when(view.isLocked()).thenReturn(false);
		final double LARGE = 1000d;
		when(lastPoint.getSource()).thenReturn(LARGE);
		when(lastPoint.getTarget()).thenReturn(LARGE);
		
		mockStartPositions();
		
		// when
		testObj.onConnectionMove(connectionMoveEvent);
		
		// then
		verify(connectionMoveEvent, times(1)).preventDefault();
		verify(view, times(1)).drawLineFromSource(anyInt(), anyInt());
	
	}
	
	@Test
	public void testOnConnectionMoveEnd_unlockedView_smallX() {
		// given
		final ConnectionMoveEvent connectionMoveEvent = mock(ConnectionMoveEvent.class);
		when(view.isLocked()).thenReturn(false);
		final double SMALL = 1d;
		final double LARGE = 1000d;
		when(lastPoint.getSource()).thenReturn(SMALL);
		when(lastPoint.getTarget()).thenReturn(LARGE);
		
		mockStartPositions();
		
		// when
		testObj.onConnectionMove(connectionMoveEvent);
		
		// then
		verify(connectionMoveEvent, times(1)).preventDefault();
		verify(view, times(1)).drawLineFromSource(anyInt(), anyInt());
	}

	@Test
	public void testOnConnectionMoveEnd_unlockedView_bothSmall() {
		// given
		final ConnectionMoveEvent connectionMoveEvent = mock(ConnectionMoveEvent.class);
		when(view.isLocked()).thenReturn(false);
		final double SMALL = 1d;
		when(lastPoint.getSource()).thenReturn(SMALL);
		when(lastPoint.getTarget()).thenReturn(SMALL);
		
		mockStartPositions();
		
		// when
		testObj.onConnectionMove(connectionMoveEvent);
		
		// then
		verify(connectionMoveEvent, times(1)).preventDefault();
		verify(view, times(0)).drawLineFromSource(anyInt(), anyInt());
		
	}
	private void mockStartPositions() {
		final HashMap<ConnectionItem, Point> startPositions = Maps.<ConnectionItem, Point>newHashMap();
		final Point point = mock(Point.class);
		startPositions.put(sourceConnectionItem, point);
		when(view.getStartPositions()).thenReturn(startPositions);
	}
}
