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
