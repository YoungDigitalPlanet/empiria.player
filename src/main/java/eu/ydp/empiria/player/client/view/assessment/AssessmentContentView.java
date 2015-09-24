package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;
import eu.ydp.empiria.player.client.view.player.PageViewCache;

public class AssessmentContentView extends Composite implements AssessmentViewSocket {

    private static AssessmentContentUiBinder uiBinder = GWT.create(AssessmentContentUiBinder.class);

    @UiField(provided = true)
    Panel assessmentPanel;
    @UiField
    FlowPanel headerPanel;
    @UiField
    FlowPanel navigationPanel;

    @UiTemplate("AssessmentContentView.ui.xml")
    interface AssessmentContentUiBinder extends UiBinder<Widget, AssessmentContentView>{
    }

    @Inject
    protected PageViewCache pageViewCache;

    @Inject
    private Page page;

    @Inject
    public AssessmentContentView(@Assisted Panel parentPanel) {
        assessmentPanel = parentPanel;
        uiBinder.createAndBindUi(this);
    }

    @Override
    public void setAssessmentViewCarrier(AssessmentViewCarrier viewCarrier) {
        headerPanel.clear();
        if (viewCarrier.getHeaderView() != null) {
            headerPanel.add(viewCarrier.getHeaderView());
        }

        navigationPanel.clear();
        if (viewCarrier.getFooterView() != null) {
            navigationPanel.add(viewCarrier.getFooterView());
        }

        if (viewCarrier.getSkinView() != null) {
            assessmentPanel.clear();
            assessmentPanel.add(headerPanel);
            assessmentPanel.add(viewCarrier.getSkinView());
            assessmentPanel.add(navigationPanel);
        }
    }

    @Override
    public PageViewSocket getPageViewSocket() {
        return pageViewCache.getOrCreateAndPut(page.getCurrentPageNumber()).getKey();
    }
}
