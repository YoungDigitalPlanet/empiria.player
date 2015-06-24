package eu.ydp.empiria.player.client.pagechangedevent;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.util.events.pagechange.PageChangedEvent;

public class PageChangedEventTest extends AbstractEmpiriaPlayerGWTTestCase {

	JSONObject expectedJson;
	PageChangedEvent pageChangedEvent;

	@Override
	protected void gwtSetUp() {
		expectedJson = new JSONObject();
		pageChangedEvent = new PageChangedEvent(1);
	}

	public void testShouldReturnSameValue_whenSameKeyIsGiven() {
		//given
		gwtSetUp();
		expectedJson.put("new_page", new JSONNumber(1));
		JSONObject payload = pageChangedEvent.getPayload();
		//then
		assertEquals(expectedJson.get("new_page"), payload.get("new_page"));
	}

	public void testShouldSetAccurateTypeAndPageNumber_whenEventIsGiven() {
		//given
		gwtSetUp();
		expectedJson.put("new_page", new JSONNumber(1));
		expectedJson.put("type", new JSONString("page_change"));
		//when
		JavaScriptObject JSObject = expectedJson.getJavaScriptObject();
		JavaScriptObject payload = pageChangedEvent.getJSObject();
		//then
		assertEquals(JSObject.toSource(), payload.toSource());
	}
}
