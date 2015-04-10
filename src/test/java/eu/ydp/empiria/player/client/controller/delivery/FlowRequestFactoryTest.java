package eu.ydp.empiria.player.client.controller.delivery;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class FlowRequestFactoryTest extends GWTTestCase {

	private FlowRequestFactory testObj = new FlowRequestFactory();

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

	public void testShouldReturnNavigateGoToItemRequestWhenItemIndexExists() {
		// given
		JSONArray state = null;
		Integer initialItemIndex = 1;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertEquals(((FlowRequest.NavigateGotoItem) result).getIndex(), (int) initialItemIndex);
	}

	public void testShouldReturnNavigateGoToItemRequestWhenStateIsNumber() {
		// given
		JSONArray state = getStateWithNumber(1);
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertEquals(((FlowRequest.NavigateGotoItem) result).getIndex(), 1);
	}

	public void testShouldReturnNullWhenArgsAreNull() {
		// given
		JSONArray state = null;
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertNull(result);
	}

	public void testShouldReturnNavigateTocRequestWhenStateIsTocType() {
		// given
		JSONArray state = getStateWithString("TOC");
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertNotNull((FlowRequest.NavigateToc) result);
	}

	public void testShouldReturnNavigateSummaryRequestWhenStateIsSummaryType() {
		// given
		JSONArray state = getStateWithString("SUMMARY");
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertNotNull((FlowRequest.NavigateSummary) result);

	}

	private JSONArray getStateWithNumber(int itemIndex) {
		JSONArray arr = new JSONArray();
		arr.isArray().set(0, new JSONNumber(itemIndex));
		return arr;
	}

	private JSONArray getStateWithString(String pageType) {
		JSONArray arr = new JSONArray();
		arr.isArray().set(0, new JSONString(pageType));
		return arr;
	}
}