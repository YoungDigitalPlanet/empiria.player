package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import com.google.gwt.json.client.JSONObject;
import com.google.gwtmockito.GwtMockito;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.compressor.LzGwtWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.json.EmpiriaStateDeserializer;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.json.JsonParserWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

    @Test
    public void testShouldDecompressState_whileCreating() throws Exception {
        // GIVEN
        String givenState = "state";
        String expectedState = "compressed";

        JSONObject parsedState = new JSONObject();
        when(jsonParser.parse(givenState)).thenReturn(parsedState);

        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.LZ_GWT, givenState);
        when(empiriaStateDeserializer.deserialize(parsedState)).thenReturn(empiriaState);

        when(lzGwtWrapper.decompress(givenState)).thenReturn(expectedState);

        // WHEN
        String result = testObj.createState(givenState);

        // THEN
        assertThat(result).isEqualTo(expectedState);
    }

    @Test
    public void testNotDecompressState_whenStateHasDefaultType() throws Exception {
        // GIVEN
        String givenState = "state";

        JSONObject parsedState = new JSONObject();
        when(jsonParser.parse(givenState)).thenReturn(parsedState);

        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.DEFAULT, givenState);
        when(empiriaStateDeserializer.deserialize(parsedState)).thenReturn(empiriaState);

        // WHEN
        String result = testObj.createState(givenState);

        // THEN
        assertThat(result).isEqualTo(givenState);
        verify(lzGwtWrapper, never()).decompress(givenState);
    }
}