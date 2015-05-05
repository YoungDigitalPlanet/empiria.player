package eu.ydp.empiria.player.client.module.external.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import eu.ydp.empiria.player.client.module.external.ExternalInteractionFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionEmpiriaApi;

public class ExternalInteractionViewImpl extends Composite implements ExternalInteractionView {

	private static ExternalInteractionViewUiBinder uiBinder = GWT.create(ExternalInteractionViewUiBinder.class);

	@UiTemplate("ExternalInteractionView.ui.xml")
	interface ExternalInteractionViewUiBinder extends UiBinder<Widget, ExternalInteractionViewImpl> {
	}

	@UiField
	ExternalInteractionFrame frame;

	public ExternalInteractionViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void init(ExternalInteractionEmpiriaApi api, ExternalInteractionFrameLoadHandler onLoadHandler) {
		frame.init(api, onLoadHandler);
	}

	@Override
	public void setUrl(String url) {
		frame.setUrl(url);
	}
}
