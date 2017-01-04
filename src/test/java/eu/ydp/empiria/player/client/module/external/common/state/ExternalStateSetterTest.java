package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalStateSetterTest {

    @InjectMocks
    private ExternalStateSetter testObj;
    @Mock
    private ExternalStateSaver stateSaver;
    @Mock
    private ExternalFrameObjectFixer frameObjectFixer;
    @Mock
    private ExternalApi externalApi;

    @Test
    public void shouldNotSetState_whenIsEmpty() {
        // given
        Optional<JavaScriptObject> jsoOptional = Optional.absent();
        when(stateSaver.getExternalState()).thenReturn(jsoOptional);

        // when
        testObj.setSavedStateInExternal(externalApi);

        // then
        verify(externalApi, never()).setStateOnExternal(any(JavaScriptObject.class));
    }

    @Test
    public void shouldSetStateOnExternal_whenIsPresent() {
        // given
        JavaScriptObject jso = mock(JavaScriptObject.class);
        JavaScriptObject fixedState = mock(JavaScriptObject.class);

        Optional<JavaScriptObject> jsoOptional = Optional.of(jso);
        when(stateSaver.getExternalState()).thenReturn(jsoOptional);
        when(frameObjectFixer.fix(jso)).thenReturn(fixedState);

        // when
        testObj.setSavedStateInExternal(externalApi);

        // then
        verify(externalApi).setStateOnExternal(fixedState);
    }

}