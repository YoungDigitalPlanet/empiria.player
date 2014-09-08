package eu.ydp.empiria.player.client.module.img.events.handlers;

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
public class TouchHandlerOnImageProviderTest {

	@InjectMocks
	private TouchHandlerOnImageProvider testObj;
	@Mock
	private PointerHandlersOnImageInitializer pointerHandlersOnImageInitializer;
	@Mock
	private TouchHandlersOnImageInitializer touchHandlersInitializer;
	@Mock
	private UserAgentUtil userAgentUtil;

	@Test
	public void shouldReturnPointerInitializer_ifIE() {
		// given
		when(userAgentUtil.isIE()).thenReturn(true);

		// when
		ITouchHandlerOnImageInitializer result = testObj.getTouchHandlersOnImageInitializer();

		// then
		assertThat(result, instanceOf(PointerHandlersOnImageInitializer.class));
	}

	@Test
	public void shouldReturnTouchInitializer_ifNotIE() {
		// given
		when(userAgentUtil.isIE()).thenReturn(false);

		// when
		ITouchHandlerOnImageInitializer result = testObj.getTouchHandlersOnImageInitializer();

		// then
		assertThat(result, instanceOf(TouchHandlersOnImageInitializer.class));
	}
}
