package eu.ydp.empiria.player.client.compressor.converters;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class BytesToHexConverterTest {

    private BytesToHexConverter testObj = new BytesToHexConverter();

    @Test
    public void shouldConvertBytesToHexString() throws Exception {
        // GIVEN
        byte[] givenBytes = new byte[]{5, 65, -17, 88};
        String expectedHexString = "0541EF58";

        // WHEN
        String result = testObj.convert(givenBytes);

        // THEN
        assertThat(result).isEqualTo(expectedHexString);
    }
}