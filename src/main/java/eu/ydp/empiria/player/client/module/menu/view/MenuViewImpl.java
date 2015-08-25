package eu.ydp.empiria.player.client.module.menu.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class MenuViewImpl extends Composite implements MenuView {

    @UiTemplate("MenuView.ui.xml")
    interface ReportViewUiBinder extends UiBinder<FlowPanel, MenuViewImpl> {
    }

    private static ReportViewUiBinder uiBinder = GWT.create(ReportViewUiBinder.class);

    @UiField
    FlowPanel menuWrapper;
    @UiField
    FlowPanel tableContainer;
    @UiField
    CustomPushButton button;

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setTable(FlexTable table) {
        tableContainer.clear();
        tableContainer.add(table);
    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        button.addClickHandler(clickHandler);
    }
}
