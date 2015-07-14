package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.NativeEventWrapper;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionsBetweenItemsFinderTest {

    @InjectMocks
    private ConnectionsBetweenItemsFinder testObj;

    @Mock
    private PositionHelper positionHelper;

    @Mock
    private NativeEventWrapper nativeEventWrapper;

    @Mock
    private NativeEvent nativeEvent;

    @Mock
    private ConnectionItems connectionItems;

    @Mock
    private IsWidget widget;

    private List<ConnectionItem> connectionItemsCollection;

    @Before
    public void setUp() {
        connectionItemsCollection = connectionItemsCollection();
        when(connectionItems.getAllItems()).thenReturn(connectionItemsCollection);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(positionHelper, nativeEventWrapper);
    }

    @Test
    public void test_clickNotInAnyItem() {
        // given
        when(positionHelper.getPoint(nativeEvent, widget)).thenReturn(point(0, 0));
        final ConnectionMoveEvent mockEvent = mock(ConnectionMoveEvent.class);
        when(mockEvent.getNativeEvent()).thenReturn(nativeEvent);

        // when
        Optional<ConnectionItem> actual = testObj.findConnectionItemForEventOnWidget(mockEvent, widget, connectionItems);

        // then
        assertFalse(actual.isPresent());
        verify(positionHelper).getPoint(nativeEvent, widget);
        verify(nativeEventWrapper, never()).preventDefault(nativeEvent);
    }

    @Test
    public void test_clickInAnItemSimple() {
        // given
        when(positionHelper.getPoint(nativeEvent, widget)).thenReturn(point(120, 60));
        final ConnectionMoveEvent mockEvent = mock(ConnectionMoveEvent.class);
        when(mockEvent.getNativeEvent()).thenReturn(nativeEvent);

        // when
        Optional<ConnectionItem> actual = testObj.findConnectionItemForEventOnWidget(mockEvent, widget, connectionItems);

        // then
        assertEquals(connectionItemsCollection.get(0), actual.get());
        verify(positionHelper).getPoint(nativeEvent, widget);
        verify(nativeEventWrapper).preventDefault(nativeEvent);

    }

    @Test
    public void test_clickBetweenItems() {
        // given
        when(positionHelper.getPoint(nativeEvent, widget)).thenReturn(point(170, 115));
        final ConnectionMoveEvent mockEvent = mock(ConnectionMoveEvent.class);
        when(mockEvent.getNativeEvent()).thenReturn(nativeEvent);

        // when
        Optional<ConnectionItem> actual = testObj.findConnectionItemForEventOnWidget(mockEvent, widget, connectionItems);

        // then
        assertFalse(actual.isPresent());
        verify(positionHelper).getPoint(nativeEvent, widget);
        verify(nativeEventWrapper, never()).preventDefault(nativeEvent);

    }

    private List<ConnectionItem> connectionItemsCollection() {
        ConnectionItem ci1 = connectionItem(10, 10, 150, 100);
        ConnectionItem ci2 = connectionItem(10, 120, 150, 100);
        ConnectionItem ci3 = connectionItem(170, 10, 150, 100);
        ConnectionItem ci4 = connectionItem(170, 120, 150, 100);

        return Lists.newArrayList(ci1, ci2, ci3, ci4);
    }

    private ConnectionItem connectionItem(int offsetLeft, int offsetTop, int width, int height) {
        ConnectionItem ci = mock(ConnectionItem.class);

        when(ci.getOffsetLeft()).thenReturn(offsetLeft);
        when(ci.getOffsetTop()).thenReturn(offsetTop);
        when(ci.getWidth()).thenReturn(width);
        when(ci.getHeight()).thenReturn(height);

        return ci;
    }

    private Point point(int x, int y) {
        return new Point(x, y);
    }
}
