package eu.ydp.empiria.player.client.compressor.converters;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class HexToByteConverterTest {

    private final HexToByteConverter testObj = new HexToByteConverter();

    @Test
    public void shouldConvertHexStringToBytes() throws Exception {
        // GIVEN
        byte[] expectedBytes = new byte[]{5, 65, -17, 88};
        String givenString = "0541EF58";

        // WHEN
        byte[] result = testObj.convert(givenString);

        // THEN
        assertThat(result).containsOnly(expectedBytes);
    }
}