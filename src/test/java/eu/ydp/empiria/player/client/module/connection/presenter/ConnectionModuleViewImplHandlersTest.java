/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionItemFluentMockBuilder;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionModuleViewImplHandlersTest {

    @InjectMocks
    private ConnectionModuleViewImplHandlers testObj;
    @Mock
    private ConnectionModuleViewImpl view;
    @Mock
    private ConnectionPairEntry<ConnectionItem, ConnectionItem> connectionPairEntry;
    @Mock
    private ConnectionPairEntry<Double, Double> lastPoint;
    @Mock
    private ConnectionPairEntry<String, String> stringConnectionPairEntry;
    @Mock
    private ConnectionItem sourceConnectionItem;
    @Mock
    private ConnectionItem targetConnectionItem;
    @Mock
    private PairChoiceBean bean;
    @Mock
    private ConnectionItemPairFinder pairFinder;
    @Mock
    private ConnectionItems connectionItems;
    @Mock
    private UserAgentCheckerWrapper userAgent;
    @Mock
    private ConnectionsBetweenItemsFinder connectionsFinder;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private PositionHelper positionHelper;
    @Mock
    private ConnectionSurfacesManager surfacesManager;

    @Before
    public void setUp() {
        testObj.setView(view);

        when(sourceConnectionItem.getColumn()).thenReturn(ConnectionItem.Column.LEFT);
        when(targetConnectionItem.getColumn()).thenReturn(ConnectionItem.Column.RIGHT);
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
    public void testOnConnectionMoveEnd_elementNotFound_mayConnect() {
        // given
        final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
        when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
                Optional.<ConnectionItem>absent());
        when(view.isLocked()).thenReturn(false);
        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.of(targetConnectionItem));

        // when
        testObj.onConnectionMoveEnd(event);

        // then
        verify(view, never()).drawLineBetween(eq(sourceConnectionItem), anyInt(), anyInt());
        verify(view, never()).connect(eq(sourceConnectionItem), any(ConnectionItem.class), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
        verify(view).clearSurface(sourceConnectionItem);
    }

    @Test
    public void testOnConnectionMoveEnd_elementNotFound_mayNotConnect() {
        // given
        final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
        when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
                Optional.<ConnectionItem>absent());
        when(view.isLocked()).thenReturn(false);
        when(connectionPairEntry.getTarget()).thenReturn(targetConnectionItem);
        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.of(targetConnectionItem));

        // when
        testObj.onConnectionMoveEnd(event);

        // then
        verify(view, never()).drawLineBetween(eq(sourceConnectionItem), anyInt(), anyInt());
        verify(view).connect(eq(sourceConnectionItem), eq(targetConnectionItem), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
        verify(view).clearSurface(sourceConnectionItem);
    }

    @Test
    public void testOnConnectionMoveEnd_elementFound() {
        // given
        final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
        when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
                Optional.of(targetConnectionItem));
        when(view.isLocked()).thenReturn(false);
        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.of(targetConnectionItem));

        // when
        testObj.onConnectionMoveEnd(event);

        // then
        verify(view).drawLineBetween(sourceConnectionItem, targetConnectionItem);
        verify(view, never()).connect(eq(sourceConnectionItem), eq(targetConnectionItem), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
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
        verify(view, times(0)).drawLineBetween(any(ConnectionItem.class), any(ConnectionItem.class));
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
        verify(view, times(1)).drawLineBetween(any(ConnectionItem.class), anyInt(), anyInt());

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
        verify(view, times(1)).drawLineBetween(eq(sourceConnectionItem), anyInt(), anyInt());
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
        verify(view, times(0)).drawLineBetween(any(ConnectionItem.class), anyInt(), anyInt());

    }

    private void mockStartPositions() {
        final HashMap<ConnectionItem, Point> startPositions = Maps.<ConnectionItem, Point>newHashMap();
        final Point point = mock(Point.class);
        startPositions.put(sourceConnectionItem, point);
        when(view.getStartPositions()).thenReturn(startPositions);
    }

    @Test
    public void testOnConnectionMoveStart_unlockedView() {
        // given
        when(view.isLocked()).thenReturn(true);
        final ConnectionMoveStartEvent event = mock(ConnectionMoveStartEvent.class);

        // when
        testObj.onConnectionStart(event);

        // then
        verifyZeroInteractions(event);
    }

    @Test
    public void testOnConnectionMoveStart_connectionNotFound_pointNotFoundOnPath() {
        // given
        final ConnectionMoveStartEvent event = mock(ConnectionMoveStartEvent.class);

        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.<ConnectionItem>absent());

        // when
        testObj.onConnectionStart(event);

        // then
        verify(eventsBus, times(0)).fireEvent(any(PlayerEvent.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnConnectionMoveStart_connectionNotFound_pointFoundOnPath() {
        // given
        final ConnectionMoveStartEvent event = mock(ConnectionMoveStartEvent.class);
        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.<ConnectionItem>absent());

        when(surfacesManager.findPointOnPath(anyMap(), any(Point.class))).thenReturn(stringConnectionPairEntry);

        // when
        testObj.onConnectionStart(event);

        // then
        verify(eventsBus, times(0)).fireEvent(any(PlayerEvent.class));
        verify(view, times(1)).disconnect(anyString(), anyString(), eq(Boolean.TRUE));
    }

    @Test
    public void testOnConnectionMoveStart_connectionFound() {
        // given
        final ConnectionMoveStartEvent event = mock(ConnectionMoveStartEvent.class);

        ConnectionItem ci = ConnectionItemFluentMockBuilder.newConnectionItem().build();
        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.of(ci));

        // when
        testObj.onConnectionStart(event);

        // then

    }

    @Test
    public void shouldCancelConnection_whenDuringEndMouseWereNotOnConnectionItem() {
        // given
        final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
        when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
                Optional.of(targetConnectionItem));
        when(view.isLocked()).thenReturn(false);
        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.<ConnectionItem>absent());

        // when
        testObj.onConnectionMoveEnd(event);

        // then
        verify(view, never()).drawLineBetween(sourceConnectionItem, targetConnectionItem);
        verify(view, never()).connect(eq(sourceConnectionItem), eq(targetConnectionItem), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
        verify(view).clearSurface(sourceConnectionItem);
        verify(view).resetTouchConnections();
    }

    @Test
    public void shouldCancelConnection_whenConnectedItemsWereInSameColumn() {
        // given
        final ConnectionMoveEndEvent event = mock(ConnectionMoveEndEvent.class);
        when(pairFinder.findConnectionItemForCoordinates(anyCollectionOf(ConnectionItem.class), anyInt(), anyInt())).thenReturn(
                Optional.of(targetConnectionItem));
        when(view.isLocked()).thenReturn(false);
        when(connectionsFinder.findConnectionItemForEventOnWidget(event, view, connectionItems)).thenReturn(Optional.of(targetConnectionItem));
        when(sourceConnectionItem.getColumn()).thenReturn(ConnectionItem.Column.LEFT);
        when(targetConnectionItem.getColumn()).thenReturn(ConnectionItem.Column.LEFT);

        // when
        testObj.onConnectionMoveEnd(event);

        // then
        verify(view, never()).drawLineBetween(sourceConnectionItem, targetConnectionItem);
        verify(view, never()).connect(eq(sourceConnectionItem), eq(targetConnectionItem), eq(MultiplePairModuleConnectType.NORMAL), (eq(Boolean.TRUE)));
        verify(view).clearSurface(sourceConnectionItem);
        verify(view).resetTouchConnections();
    }

}
