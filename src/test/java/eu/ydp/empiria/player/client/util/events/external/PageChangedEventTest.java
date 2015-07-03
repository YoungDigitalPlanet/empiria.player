package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class PageChangedEventTest extends AbstractEmpiriaPlayerGWTTestCase {

    public static final int EXPECTED_PAGE_INDEX = 7;
    public static final double DELTA = 0.1;

    public void testShouldSerializeEventWithTypeAndPageIndex() {
        // given
        PageChangedEvent testObj = new PageChangedEvent(EXPECTED_PAGE_INDEX);

        // when
        JavaScriptObject actual = testObj.getJSONObject();
        JSONObject actualJSON = new JSONObject(actual);

        // then
        double actualPage = actualJSON.get("payload").isObject().get("new_page").isNumber().doubleValue();
        assertEquals(actualPage, EXPECTED_PAGE_INDEX, DELTA);

        String actualEventType = actualJSON.get("type").isString().stringValue();
        assertEquals(actualEventType, "page_changed");
    }
}
