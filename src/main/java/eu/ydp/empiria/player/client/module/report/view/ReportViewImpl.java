package eu.ydp.empiria.player.client.module.report.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;

public class ReportViewImpl extends Composite implements ReportView{

    @UiTemplate("ReportView.ui.xml")
    interface ReportViewUiBinder extends UiBinder<FlowPanel, ReportViewImpl> {
    }

    private static ReportViewUiBinder uiBinder = GWT.create(ReportViewUiBinder.class);

    @UiField
    FlowPanel report;

    public ReportViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setTable(FlexTable table) {
        report.clear();
        report.add(table);
    }
}
