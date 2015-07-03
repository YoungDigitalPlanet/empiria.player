package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class TouchHandlerProviderTest {

    @InjectMocks
    private TouchHandlerProvider testObj;

    @Mock
    private PointerHandlersInitializer pointerHandlersInitializer;

    @Mock
    private TouchHandlersInitializer touchHandlersInitializer;

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
