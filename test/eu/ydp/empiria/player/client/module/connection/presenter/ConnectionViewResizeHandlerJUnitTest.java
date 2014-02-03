package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;

@SuppressWarnings("PMD")
public class ConnectionViewResizeHandlerJUnitTest {

	private final ConnectionViewResizeHandler instance = new ConnectionViewResizeHandler();
	private final ConnectionEventHandler eventHandler = mock(ConnectionEventHandler.class);

	@Test
	public void testOnResize_eventHandlerWasSet() {
		instance.setConnectionModuleViewImpl(eventHandler);
		instance.onResize(null);
		verify(eventHandler).fireConnectEvent(Matchers.eq(PairConnectEventTypes.REPAINT_VIEW), Matchers.anyString(), Matchers.anyString(), Matchers.eq(true));
	}

	@Test
	public void testOnResize_noEventHandler() {
		instance.onResize(null);
		Mockito.verifyZeroInteractions(eventHandler);
	}

}
