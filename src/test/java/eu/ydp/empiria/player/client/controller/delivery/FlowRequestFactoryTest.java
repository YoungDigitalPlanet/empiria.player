package eu.ydp.empiria.player.client.controller.delivery;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class FlowRequestFactoryTest extends GWTTestCase {

	private FlowRequestFactory testObj;

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		testObj = new FlowRequestFactory();
	}

	@Test
	public void shouldReturnNavigateGoToItemRequestWhenItemIndexExists() {
		// given
		JSONArray state = null;
		Integer initialItemIndex = 1;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertThat(((FlowRequest.NavigateGotoItem) result).getIndex()).equals(initialItemIndex);
	}

	@Test
	public void shouldReturnNavigateGoToItemRequestWhenStateIsNumber() {
		// given
		JSONArray state = getStateWithNumber(1);
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertThat(((FlowRequest.NavigateGotoItem) result).getIndex()).equals(initialItemIndex);
	}

	@Test
	public void shouldReturnNullWhenArgsAreNull() {
		// given
		JSONArray state = null;
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertThat(result).isNull();
	}

	@Test
	public void shouldReturnNavigateTocRequestWhenStateIsTocType() {
		// given
		JSONArray state = getStateWithString("TOC");
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertThat(result).isInstanceOf(FlowRequest.NavigateToc.class);
	}

	@Test
	public void shouldReturnNavigateSummaryRequestWhenStateIsSummaryType() {
		// given
		JSONArray state = getStateWithString("SUMMARY");
		Integer initialItemIndex = null;

		// when
		IFlowRequest result = testObj.create(state, initialItemIndex);

		// then
		assertThat(result).isInstanceOf(FlowRequest.NavigateSummary.class);

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