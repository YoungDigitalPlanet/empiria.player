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

package eu.ydp.empiria.player.client.view.page;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.communication.PageDataError;
import eu.ydp.empiria.player.client.controller.communication.PageDataSummary;
import eu.ydp.empiria.player.client.controller.communication.PageDataToC;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequestSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.SessionDataSocket;

/**
 * Contains all data that is required to create Page View
 */
public class PageViewCarrier {
    public PageType pageType;

    public String[] titles;
    public SessionDataSocket sessionDataSocket;
    public String errorMessage;

    public IFlowRequestSocket flowRequestSocket;

    public PageViewCarrier() {
        pageType = PageType.TEST;
    }

    public PageViewCarrier(PageDataToC pageDataToc, IFlowRequestSocket frs) {
        pageType = PageType.TOC;
        titles = pageDataToc.titles;
        flowRequestSocket = frs;
    }

    public PageViewCarrier(PageDataSummary pageDataSummary, IFlowRequestSocket frs) {
        pageType = PageType.SUMMARY;
        titles = pageDataSummary.titles;
        sessionDataSocket = pageDataSummary.sessionData;
        flowRequestSocket = frs;
    }

    public PageViewCarrier(PageDataError pageDataError) {
        pageType = PageType.ERROR;
        errorMessage = pageDataError.errorMessage;
    }

    public Widget getPageTitle() {
        return new Label("");
    }

    public boolean hasContent() {
        return pageType != PageType.TEST;
    }
}
