package eu.ydp.empiria.player.client.view.player;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.view.assessment.AssessmentContentView;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;

public class PlayerContentView extends Composite implements PlayerViewSocket {
	private static PlayerContentViewUiBinder uiBinder = GWT.create(PlayerContentViewUiBinder.class);

	interface PlayerContentViewUiBinder extends UiBinder<Widget, PlayerContentView> {
	}

	private final AssessmentContentView assessmentContentView; // NOPMD

	@UiField
	protected Panel headerPanel;
	@UiField
	protected FlowPanel assessmentPanel = null;
	@UiField
	protected Panel footerPanel;

	public PlayerContentView() {
		initWidget(uiBinder.createAndBindUi(this));
		assessmentContentView = new AssessmentContentView(assessmentPanel);
	}

	@Override
	public void setPlayerViewCarrier(PlayerViewCarrier pvd) {
		headerPanel.clear();
		headerPanel.add(pvd.getHeaderView());
		footerPanel.clear();
		footerPanel.add(pvd.getFooterView());

	}

	@Override
	public AssessmentViewSocket getAssessmentViewSocket() {
		return assessmentContentView;
	}
}
