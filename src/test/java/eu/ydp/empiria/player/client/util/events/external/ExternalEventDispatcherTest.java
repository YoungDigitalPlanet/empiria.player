package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.core.client.JavaScriptObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalEventDispatcherTest {

	@InjectMocks
	private ExternalEventDispatcher testObj;
	@Mock
	private ExternalEvent externalEvent;
	@Mock
	private ExternalCallback callback;
	@Mock
	private JavaScriptObject jsEvent;

	@Test
	public void shouldDispatchEvent() {
		// given
		when(externalEvent.getJSONObject()).thenReturn(jsEvent);
		testObj.setCallbackFunction(callback);

		// when
		testObj.dispatch(externalEvent);

		// then
		verify(callback).callback(jsEvent);
	}

	@Test
	public void shouldNotDispatchEvent_ifTheCallbackIsNotSet() {
		// when
		testObj.dispatch(externalEvent);

		// then
		verifyZeroInteractions(callback);
	}
}
