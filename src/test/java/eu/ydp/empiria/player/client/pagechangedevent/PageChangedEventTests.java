package eu.ydp.empiria.player.client.pagechangedevent;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.util.events.pagechange.EventDispatcher;
import eu.ydp.empiria.player.client.util.events.pagechange.PageChangedEvent;

public class PageChangedEventTests extends AbstractEmpiriaPlayerGWTTestCase {
	JSONObject jsonObject;
	PageChangedEvent pageChangedEvent;
	EventDispatcher eventDispatcher;

	private void prepare() {
		jsonObject = new JSONObject();
		pageChangedEvent = new PageChangedEvent(1);
		eventDispatcher = new EventDispatcher();

	}

	public void testShouldReturnSameValue_whenSameKeyIsGiven() {
		//given
		prepare();
		jsonObject.put("new_page", new JSONNumber(1));
		JSONObject payload = pageChangedEvent.getPayload();
		//when
		//then
		assertEquals(jsonObject.get("new_page"), payload.get("new_page"));

	}

	public void testShouldSetAccurateTypeAndPageNumber_whenEventIsGiven() {
		//given
		prepare();
		jsonObject.put("new_page", new JSONNumber(1));
		jsonObject.put("type", new JSONString("page_change"));
		//when
		JavaScriptObject JSObject = jsonObject.getJavaScriptObject();
		JavaScriptObject payload = pageChangedEvent.getJSObject();
		//then
		assertEquals(JSObject.toSource(), payload.toSource());
	}
}
