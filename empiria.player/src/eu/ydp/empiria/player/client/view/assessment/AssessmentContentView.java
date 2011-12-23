package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

import eu.ydp.empiria.player.client.view.page.PageContentView;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;

public class AssessmentContentView implements AssessmentViewSocket {

	public AssessmentContentView(Panel ap){
		assessmentPanel = ap;
		pagePanel = new FlowPanel();
		pagePanel.setStyleName("qp-body");
		headerPanel = new FlowPanel();
		headerPanel.setStyleName("qp-header");
		navigationPanel = new FlowPanel();
		navigationPanel.setStyleName("qp-footer");
		
		assessmentPanel.add(headerPanel);
		assessmentPanel.add(pagePanel);
		assessmentPanel.add(navigationPanel);
		
		pageContentView = new PageContentView(pagePanel);
		
	}
	
	private PageContentView pageContentView;
	
	private Panel assessmentPanel;
	private Panel headerPanel;
	private Panel pagePanel;
	private Panel navigationPanel;


	@Override
	public void setAssessmentViewCarrier(AssessmentViewCarrier a) {
		headerPanel.clear();
		if (a.getHeaderView() != null)
			headerPanel.add(a.getHeaderView());
		navigationPanel.clear();
		if (a.getFooterView() != null)
			navigationPanel.add(a.getFooterView());
		
	}

	@Override
	public PageViewSocket getPageViewSocket() {
		return pageContentView;
	}


}
