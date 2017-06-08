/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.delivery;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class FlowRequestFactoryGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private FlowRequestFactory testObj = new FlowRequestFactory();

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
