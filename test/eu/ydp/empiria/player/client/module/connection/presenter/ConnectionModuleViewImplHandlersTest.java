package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;

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
	
	@Before
	public void setUp() {
		testObj.setView(view);
		
		when(sourceConnectionItem.getBean()).thenReturn(bean);
		when(view.getConnectionItemPair()).thenReturn(connectionPairEntry);
		when(connectionPairEntry.getSource()).thenReturn(sourceConnectionItem);
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
	public void testOnConnectionMove() {
		// given
		final ConnectionMoveEvent event = mock(ConnectionMoveEvent.class);
		
		// when
		testObj.onConnectionMove(event);
		
		// then
	}
}
