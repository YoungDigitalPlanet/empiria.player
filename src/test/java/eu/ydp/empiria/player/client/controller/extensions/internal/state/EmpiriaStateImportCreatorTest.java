package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import com.google.gwt.json.client.JSONObject;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.compressor.LzGwtWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.json.EmpiriaStateDeserializer;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.json.JsonParserWrapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class EmpiriaStateImportCreatorTest {

    @InjectMocks
    private EmpiriaStateImportCreator testObj;
    @Mock
    private EmpiriaStateDeserializer empiriaStateDeserializer;
    @Mock
    private LzGwtWrapper lzGwtWrapper;
    @Mock
    private JsonParserWrapper jsonParser;
    @Mock
    private EmpiriaStateVerifier empiriaStateVerifier;

    @Test
    public void shouldDecompressState_whileCreating() throws Exception {
        // GIVEN
        String givenState = "state";
        String expectedState = "compressed";

        JSONObject parsedState = new JSONObject();
        when(jsonParser.parse(givenState)).thenReturn(parsedState);

        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.LZ_GWT, givenState, "id");
        when(empiriaStateVerifier.verifyState(empiriaState)).thenReturn(empiriaState);
        when(empiriaStateDeserializer.deserialize(parsedState)).thenReturn(empiriaState);

        when(lzGwtWrapper.decompress(givenState)).thenReturn(expectedState);

        // WHEN
        String result = testObj.createState(givenState);

        // THEN
        assertThat(result).isEqualTo(expectedState);
    }

    @Test
    public void shouldReturnEmptyState_whenStateIsUnknown() throws Exception {
        // GIVEN
        String givenState = "state";

        JSONObject parsedState = new JSONObject();
        when(jsonParser.parse(givenState)).thenReturn(parsedState);

        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.UNKNOWN, givenState, StringUtils.EMPTY);
        when(empiriaStateVerifier.verifyState(empiriaState)).thenReturn(empiriaState);
        when(empiriaStateDeserializer.deserialize(parsedState)).thenReturn(empiriaState);

        // WHEN
        String result = testObj.createState(givenState);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldDoNothingWithState_whenStateHasOldType() throws Exception {
        // GIVEN
        String givenState = "state";

        JSONObject parsedState = new JSONObject();
        when(jsonParser.parse(givenState)).thenReturn(parsedState);

        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.OLD, givenState, StringUtils.EMPTY);
        when(empiriaStateVerifier.verifyState(empiriaState)).thenReturn(empiriaState);
        when(empiriaStateDeserializer.deserialize(parsedState)).thenReturn(empiriaState);

        // WHEN
        String result = testObj.createState(givenState);

        // THEN
        assertThat(result).isEqualTo(givenState);
        verify(lzGwtWrapper, never()).decompress(givenState);
    }
}