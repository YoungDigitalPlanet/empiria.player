package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ProgressPanel extends Composite {

	private static ProgressPanelUiBinder uiBinder = GWT.create(ProgressPanelUiBinder.class);

	interface ProgressPanelUiBinder extends UiBinder<Widget, ProgressPanel> {
	}

	@UiField
	protected FlowPanel panel;

	public ProgressPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		ProgressBundle progressBundle = GWT.create(ProgressBundle.class);
		Image image = new Image(progressBundle.getProgressImage());
		panel.add(image);
	}

}
