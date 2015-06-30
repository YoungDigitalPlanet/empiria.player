package eu.ydp.empiria.player.client.module.external.presentation.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalFrame;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;

public class ExternalPresentationViewImpl extends Composite implements ExternalView<ExternalApi, ExternalEmpiriaApi> {

    private static ExternalInteractionViewUiBinder uiBinder = GWT.create(ExternalInteractionViewUiBinder.class);

    @UiTemplate("ExternalPresentationView.ui.xml")
    interface ExternalInteractionViewUiBinder extends UiBinder<Widget, ExternalPresentationViewImpl> {
    }

    @UiField
    ExternalFrame<ExternalApi> frame;

    @Inject
    public ExternalPresentationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void init(ExternalEmpiriaApi api, ExternalFrameLoadHandler<ExternalApi> onLoadHandler, String url) {
        frame.init(api, onLoadHandler);
        frame.setUrl(url);
    }
}
