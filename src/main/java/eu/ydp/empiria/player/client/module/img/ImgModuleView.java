package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ImgModuleView extends Composite {

    private static ImgModuleViewUiBinder uiBinder = GWT.create(ImgModuleViewUiBinder.class);

    interface ImgModuleViewUiBinder extends UiBinder<Widget, ImgModuleView> {
    }

    @UiField
    protected Panel containerPanel;
    @UiField
    protected Panel titlePanel;
    @UiField
    protected Panel descriptionPanel;
    @UiField
    protected Panel contentPanel;

    public ImgModuleView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setContent(IsWidget content) {
        contentPanel.add(content);
    }

    public void setTitle(IsWidget title) {
        titlePanel.add(title);
    }

    public void setDescription(IsWidget description) {
        descriptionPanel.add(description);
    }

    public Panel getContainerPanel() {
        return containerPanel;
    }

}
