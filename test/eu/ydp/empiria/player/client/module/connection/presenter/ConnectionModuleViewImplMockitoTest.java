package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.dom.client.NativeEvent;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionModuleViewImplMockitoTest {

	@InjectMocks
	private ConnectionModuleViewImpl testObj;
	
	@Test
	public void connectByClickTest() {
		// given
		NativeEvent event = mock(NativeEvent.class);
		
		
//		Set<ConnectionItem> connectionItems = testObj.connectionItems.getConnectionItems(null);
//		Iterator<ConnectionItem> iterator = connectionItems.iterator();
//		ConnectionItem item1 = iterator.next();
//		ConnectionItem item2 = iterator.next();
//		// doReturn(item1).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
//		testObj.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
//		testObj.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
//		// doReturn(item2).when(connectionsBetweenItems).findConnectionItem(Mockito.any(NativeEvent.class));
//		testObj.onConnectionStart(new ConnectionMoveStartEvent(0, 0, event, null));
//		testObj.onConnectionMoveEnd(new ConnectionMoveEndEvent(1, 1, event));
//		verify(testObj).resetConnectionMadeByTouch();
//		verify(testObj).connect(eq(item2), eq(item1), eq(MultiplePairModuleConnectType.NORMAL), eq(true));
	}

}
