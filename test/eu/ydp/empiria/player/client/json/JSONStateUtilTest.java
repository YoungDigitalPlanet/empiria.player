package eu.ydp.empiria.player.client.json;

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class JSONStateUtilTest extends AbstractEmpiriaPlayerGWTTestCase {

	private JSONStateUtil testObj = new JSONStateUtil();

	public void testShouldGetCreatedString() {
		// given
		final String EXPECTED = "value";
		JSONArray state = testObj.createWithString(EXPECTED);

		// when
		String result = testObj.extractString(state);

		// then
		assertEquals(result, EXPECTED);
	}
}
