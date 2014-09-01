package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.gwtutil.client.util.UserAgentUtil;

@RunWith(GwtMockitoTestRunner.class)
public class TouchHandlerProviderTest {

	@InjectMocks
	private TouchHandlerProvider testObj;

	@Mock
	private UserAgentUtil userAgentUtil;

	@Test
	public void shouldReturnPointerInitializer_ifIE() {
		// given
		when(userAgentUtil.isIE()).thenReturn(true);

		// when
		ITouchHandlerInitializer result = testObj.getTouchHandlersInitializer();

		// then
		assertThat(result, instanceOf(PointerHandlersInitializer.class));
	}

	@Test
	public void shouldReturnTouchInitializer_ifNotIE() {
		// given
		when(userAgentUtil.isIE()).thenReturn(false);

		// when
		ITouchHandlerInitializer result = testObj.getTouchHandlersInitializer();

		// then
		assertThat(result, instanceOf(TouchHandlersInitializer.class));
	}
}
