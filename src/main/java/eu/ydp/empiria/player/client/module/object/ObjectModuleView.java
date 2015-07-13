package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ObjectModuleView extends Composite {

    private static ObjectModuleViewUiBinder uiBinder = GWT.create(ObjectModuleViewUiBinder.class);

    interface ObjectModuleViewUiBinder extends UiBinder<Widget, ObjectModuleView> {
    }

    @UiField
    protected FlowPanel containerPanel;

    @UiField
    protected FlowPanel contentPanel;

    @UiField
    protected FlowPanel titlePanel;

    @UiField
    protected FlowPanel descriptionPanel;

    public ObjectModuleView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public FlowPanel getContainerPanel() {
        return containerPanel;
    }

    public FlowPanel getContentPanel() {
        return contentPanel;
    }

    public FlowPanel getDescriptionPanel() {
        return descriptionPanel;
    }

    public void setTitleWidget(IsWidget w) {
        titlePanel.clear();
        titlePanel.add(w);
    }
}
