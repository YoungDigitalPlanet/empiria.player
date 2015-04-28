package eu.ydp.empiria.player.client.module.external.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class ExternalInteractionViewImpl implements ExternalInteractionView {

	private static ExternalInteractionViewUiBinder uiBinder = GWT.create(ExternalInteractionViewUiBinder.class);

	@UiTemplate("ExternalInteractionView.ui.xml")
	interface ExternalInteractionViewUiBinder extends UiBinder<Widget, ExternalInteractionViewImpl> {
	}

	@UiField
	Panel mainPanel;

	@UiField
	Frame frame;

	private final Widget widget;

	public ExternalInteractionViewImpl() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
