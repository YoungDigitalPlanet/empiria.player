package eu.ydp.empiria.player.client.view.player;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.view.assessment.AssessmentContentView;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;

public class PlayerContentView implements PlayerViewSocket {

	public PlayerContentView(){
		playerPanel = new FlowPanel();
		playerPanel.setStyleName("qp-player");
		headerPanel = new FlowPanel();
		headerPanel.setStyleName("qp-player-header");
		assessmentPanel = new FlowPanel();
		assessmentPanel.setStyleName("qp-player-body");
		footerPanel = new FlowPanel();
		footerPanel.setStyleName("qp-player-footer");
		
		playerPanel.add(headerPanel);
		playerPanel.add(assessmentPanel);
		playerPanel.add(footerPanel);
		
		assessmentContentView = new AssessmentContentView(assessmentPanel);
	}

	public Widget getView(){
		return playerPanel;
	}

	private AssessmentContentView assessmentContentView;
	private Panel playerPanel;
	private Panel headerPanel;
	private Panel assessmentPanel;
	private Panel footerPanel;

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
