package eu.ydp.empiria.player.client.compressor;

import eu.ydp.empiria.player.client.compressor.converters.BytesToHexConverter;
import eu.ydp.empiria.player.client.compressor.converters.HexToByteConverter;
import eu.ydp.gwtutil.client.debug.log.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LzGwtWrapperTest {

    @InjectMocks
    private LzGwtWrapper testObj;
    @Mock
    private HexToByteConverter hexToByteConverter;
    @Mock
    private BytesToHexConverter bytesToHexConverter;
    @Mock
    private Logger logger;

    @Test
    public void shouldCompressString() throws Exception {
        // GIVEN
        String givenValue = "a";
        String expectedString = "compressed";

        when(bytesToHexConverter.convert(getByteArray())).thenReturn(expectedString);

        // WHEN
        String result = testObj.compress(givenValue);

        // THEN
        assertThat(result).isEqualTo(expectedString);
    }

    @Test
    public void shouldDecompressString() throws Exception {
        // GIVEN
        String givenValue = "compressed";
        String expectedValue = "a";

        when(hexToByteConverter.convert(givenValue)).thenReturn(getByteArray());

        // WHEN
        String result = testObj.decompress(givenValue);

        // THEN
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldReturnEmptyString_andLogError_whenUnableToDecompress() throws Exception {
        // GIVEN
        String givenValue = "compressed";
        when(hexToByteConverter.convert(givenValue)).thenReturn(new byte[]{});

        // WHEN
        String result = testObj.decompress(givenValue);

        // THEN
        verify(logger).error("Unable to decompress state " + givenValue);
        assertThat(result).isEmpty();

    }

    private byte[] getByteArray() {
        return new byte[]{
                93, 0, 0, 8, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 48, -63, -5, -1, -1, -1, -32, 0, 0, 0
        };
    }
}