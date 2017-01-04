package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalFrameObjectFixer;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSetter;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalPresentationPresenterTest {

    @InjectMocks
    private ExternalPresentationPresenter testObj;
    @Mock
    private ExternalView<ExternalApi, ExternalEmpiriaApi> view;
    @Mock
    private ExternalEmpiriaApi empiriaApi;
    @Mock
    private ExternalApi externalApi;
    @Mock
    private ExternalPaths externalPaths;
    @Mock
    private ExternalStateSaver stateSaver;
    @Mock
    private ExternalStateEncoder stateEncoder;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private ExternalStateSetter externalStateSetter;
    @Captor
    private ArgumentCaptor<PlayerEventHandler> playerEventHandlerCaptor;

    private final static String EXPECTED_URL = "EXPECTED_URL";

    @Test
    public void shouldInitializeView() {
        // given
        when(externalPaths.getExternalEntryPointPath()).thenReturn(EXPECTED_URL);

        // when
        testObj.init();

        // then
        verify(view).init(empiriaApi, testObj, EXPECTED_URL);
    }

    @Test
    public void shouldSetStateOnModuleLoaded() throws Exception {
        // WHEN
        testObj.onExternalModuleLoaded(externalApi);

        // THEN
        verify(externalStateSetter).setSavedStateInExternal(externalApi);
    }

    @Test
    public void shouldSetState() {
        // given
        JavaScriptObject jsObj = mock(JavaScriptObject.class);
        JSONArray array = mock(JSONArray.class);
        when(stateEncoder.decodeState(array)).thenReturn(jsObj);

        // when
        testObj.setState(array);

        // then
        verify(stateSaver).setExternalState(jsObj);
    }

    @Test
    public void shouldReturnState() {
        // given
        Optional<JavaScriptObject> externalState = Optional.absent();
        when(stateSaver.getExternalState()).thenReturn(externalState);

        JavaScriptObject jsObj = mock(JavaScriptObject.class);
        when(externalApi.getStateFromExternal()).thenReturn(jsObj);
        testObj.onExternalModuleLoaded(externalApi);

        JSONArray jsonArray = mock(JSONArray.class);
        when(stateEncoder.encodeState(jsObj)).thenReturn(jsonArray);

        // when
        JSONArray array = testObj.getState();

        // then
        assertThat(array).isEqualTo(jsonArray);
    }

    @Test
    public void shouldSetProperOnPageChangeHandler() throws Exception {
        // GIVEN
        String givenExternalPath = "given/extenal/path";
        when(externalPaths.getExternalEntryPointPath()).thenReturn(givenExternalPath);

        // WHEN
        testObj.addHandlers();

        // THEN
        verify(eventsBus).addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), playerEventHandlerCaptor.capture());

        PlayerEventHandler addedHandler = playerEventHandlerCaptor.getValue();

        addedHandler.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_CHANGE));
        verify(view).setIframeUrl(givenExternalPath);

    }
}
