package eu.ydp.empiria.player.client.util;

import static junitparams.JUnitParamsRunner.*;
import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class MimeUtilTest {

	private final MimeUtil testObj = new MimeUtil();

	Object[] data() {
		return $($("test.mp3", "audio/mp4"), $("test.ogg", "audio/ogg"), $("test.ogv", "audio/ogg"), $("test.ukn", ""));
	}

	@Test
	@Parameters(method = "data")
	public void shouldReturnValidMimeFromFileExtension(String file, String mime) {
		// when
		String result = testObj.getMimeTypeFromExtension(file);

		// then
		assertEquals(result, mime);
	}
}
