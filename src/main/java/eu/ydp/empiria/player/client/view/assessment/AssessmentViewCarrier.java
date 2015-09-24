package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.Assessment;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class AssessmentViewCarrier {

    private final Assessment assessment;

    public AssessmentViewCarrier(Assessment assessment, ViewSocket hvs, ViewSocket fvs) {
        headerViewSocket = hvs;
        footerViewSocket = fvs;
        this.assessment = assessment;
    }

    private final ViewSocket headerViewSocket;
    private final ViewSocket footerViewSocket;

    public Widget getHeaderView() {
        return (headerViewSocket == null) ? null : headerViewSocket.getView(); // NOPMD
    }

    public Widget getSkinView() {
        return assessment.getSkinView();
    }

    public Widget getFooterView() {
        return (footerViewSocket == null) ? null : footerViewSocket.getView(); // NOPMD
    }

}
