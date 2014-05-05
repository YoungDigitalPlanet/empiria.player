package eu.ydp.empiria.player.client.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MimeUtilTest {

	private final MimeUtil testObj = new MimeUtil();

	@Parameters
	public static Iterable<Object[]> data() {
		Object[][] data = new Object[][] { { "test.mp3", "audio/mp4" }, { "test.ogg", "audio/ogg" }, { "test.ogv", "audio/ogg" }, { "test.ukn", "" } };

		return Arrays.asList(data);
	}

	@Parameter
	public String file;

	@Parameter(value = 1)
	public String mime;

	@Test
	public void shouldReturnValidMimeFromFileExtension() {
		// when
		String result = testObj.getMimeTypeFromExtension(file);

		// then
		assertEquals(result, mime);
	}
}
