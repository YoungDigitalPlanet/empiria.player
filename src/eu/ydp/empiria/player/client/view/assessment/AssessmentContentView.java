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

	protected Panel assessmentPanel;
	protected FlowPanel headerPanel;
	protected FlowPanel navigationPanel;
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	protected PageViewCache pageViewCache = PlayerGinjector.INSTANCE.getPageViewCache();
	private final Page page = PlayerGinjector.INSTANCE.getPage();
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
	}

	@Override
	public PageViewSocket getPageViewSocket() {
		return pageViewCache.get(page.getCurrentPageNumber()).getKey();
	}

}
