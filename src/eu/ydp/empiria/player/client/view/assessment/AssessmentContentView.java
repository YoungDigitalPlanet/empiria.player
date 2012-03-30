package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

import eu.ydp.empiria.player.client.view.page.PageContentView;
import eu.ydp.empiria.player.client.view.page.PageViewCarrier;
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
	public void setAssessmentViewCarrier(AssessmentViewCarrier viewCarrier) {
		Panel pageSlot = viewCarrier.getPageSlot();
		
		headerPanel.clear();
		if (viewCarrier.getHeaderView() != null)
			headerPanel.add(viewCarrier.getHeaderView());
		navigationPanel.clear();
		if (viewCarrier.getFooterView() != null)
			navigationPanel.add(viewCarrier.getFooterView());
		
		if(viewCarrier.getSkinView() != null){
			assessmentPanel.clear();
			assessmentPanel.add(headerPanel);
			assessmentPanel.add(viewCarrier.getSkinView());
			assessmentPanel.add(navigationPanel);
		}
		
		if(pageSlot != null){
			PageViewCarrier carrier = new PageViewCarrier(pageSlot);
			pageContentView.setPageViewCarrier(carrier);
		}
	}

	@Override
	public PageViewSocket getPageViewSocket() {
		return pageContentView;
	}


}
