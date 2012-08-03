package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;
import eu.ydp.empiria.player.client.view.player.PageViewCache;

public class AssessmentContentView extends Composite implements AssessmentViewSocket {

	protected FlowPanel headerPanel;
	protected FlowPanel navigationPanel;
	protected PageViewCache pageViewCache = PlayerGinjector.INSTANCE.getPageViewCache();
	protected Panel assessmentPanel;
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();

	private void createView() {
		headerPanel = new FlowPanel();
		headerPanel.setStyleName(styleNames.QP_HEADER());
		navigationPanel = new FlowPanel();
		navigationPanel.setStyleName(styleNames.QP_FOOTER());
	}

	public AssessmentContentView(Panel parentPanel) {
		createView();
		assessmentPanel = parentPanel;
	}

	@Override
	public void setAssessmentViewCarrier(AssessmentViewCarrier viewCarrier) {
		Panel pageSlot = viewCarrier.getPageSlot();
		headerPanel.clear();
		if (viewCarrier.getHeaderView() != null) {
			headerPanel.add(viewCarrier.getHeaderView());
		}
		navigationPanel.clear();
		if (viewCarrier.getFooterView() != null) {
			navigationPanel.add(viewCarrier.getFooterView());
		}
		if (viewCarrier.getSkinView() != null) {
			assessmentPanel.clear();
			assessmentPanel.add(headerPanel);
			assessmentPanel.add(viewCarrier.getSkinView());
			assessmentPanel.add(navigationPanel);
		}

		if (pageSlot != null ) {
	//		PageViewCarrier carrier = new PageViewCarrier(pageSlot);
		//	PageViewSocket pageContentView = pageViewCache.get(Page.getCurrentPageNumber()).getKey();
		//	pageContentView.setPageViewCarrier(carrier);
		}
	}

	@Override
	public PageViewSocket getPageViewSocket() {
		// FIXME poprawa zwracanego pageview to jest w controlerze
		// FIXME do sprawdzenia czy zwraca dabra referencje do pagecontrollera
		return pageViewCache.get(Page.getCurrentPageNumber()).getKey();
	}

}
