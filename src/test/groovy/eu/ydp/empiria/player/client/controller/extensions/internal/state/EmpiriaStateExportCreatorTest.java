package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import eu.ydp.empiria.player.client.compressor.LzGwtWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmpiriaStateExportCreatorTest {

    @InjectMocks
    private EmpiriaStateExportCreator testObj;
    @Mock
    private LzGwtWrapper lzGwtWrapper;

    @Test
    public void shouldCreateCompressedState() throws Exception {
        // GIVEN
        String givenState = "given state";
        String expectedState = "compressed";

        when(lzGwtWrapper.compress(givenState)).thenReturn(expectedState);

        // WHEN
        EmpiriaState result = testObj.create(givenState);

        // THEN
        assertThat(result.getFormatType()).isEqualTo(EmpiriaStateType.LZ_GWT);
        assertThat(result.getState()).isEqualTo(expectedState);
    }
}