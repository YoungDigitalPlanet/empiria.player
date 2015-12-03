package eu.ydp.empiria.player.client.module.audio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class AudioModuleView extends Composite {

    private static AudioModuleViewUiBinder uiBinder = GWT.create(AudioModuleViewUiBinder.class);

    interface AudioModuleViewUiBinder extends UiBinder<Widget, AudioModuleView> {
    }

    @UiField
    protected FlowPanel containerPanel;

    public AudioModuleView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public FlowPanel getContainerPanel() {
        return containerPanel;
    }

}
