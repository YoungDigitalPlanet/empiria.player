package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import eu.ydp.empiria.player.client.module.connection.ConnectionItemFluentMockBuilder;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static eu.ydp.empiria.player.client.module.connection.ConnectionItemFluentMockBuilder.newConnectionItem;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionItemPairFinderTest {

    @InjectMocks
    private ConnectionItemPairFinder testObj;
    private ConnectionItem item1;
    private ConnectionItem item2;
    private ConnectionItem item3;
    private ConnectionItem item4;
    private Set<ConnectionItem> connectionItems;

    @Before
    public void setUp() {
        ConnectionItemFluentMockBuilder builder = newConnectionItem().withDimensions(200, 100);
        item1 = builder.withOffsets(0, 0).build();
        item2 = builder.withOffsets(300, 0).build();

        item3 = builder.withOffsets(0, 200).build();
        item4 = builder.withOffsets(300, 200).build();

        connectionItems = Sets.newHashSet(item1, item2, item3, item4);
    }

    @Test
    public void testFindEndConnectionItem_onEmptySet() {
        // given
        Set<ConnectionItem> connectionItems = Sets.newHashSet();

        // when
        Optional<ConnectionItem> result = testObj.findConnectionItemForCoordinates(connectionItems, 0, 0);

        // then
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindEndConnection_firstMatching() {
        // when
        Optional<ConnectionItem> item = testObj.findConnectionItemForCoordinates(connectionItems, 100, 50);

        // then
        assertTrue(item.isPresent());
        assertEquals(item1, item.get());
    }

    @Test
    public void testFindEndConnection_secondMatching() {
        // when
        Optional<ConnectionItem> item = testObj.findConnectionItemForCoordinates(connectionItems, 200, 50);

        // then
        assertTrue(item.isPresent());
        assertEquals(item1, item.get());
    }

    @Test
    public void testFindEndConnection_lastMatching() {
        // when
        Optional<ConnectionItem> item = testObj.findConnectionItemForCoordinates(connectionItems, 400, 250);

        // then
        assertTrue(item.isPresent());
        assertEquals(item4, item.get());
    }

    @Test
    public void testFindEndConnection_inTheMiddleNoMatching() {
        // when
        Optional<ConnectionItem> item = testObj.findConnectionItemForCoordinates(connectionItems, 250, 150);

        // then
        assertFalse(item.isPresent());
    }
}
