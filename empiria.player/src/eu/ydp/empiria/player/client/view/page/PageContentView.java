package eu.ydp.empiria.player.client.view.page;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class PageContentView extends Composite {
	private Panel pagePanel;
	protected Panel itemsPanel = new FlowPanel();
	protected Panel titlePanel = new FlowPanel();
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();

	public PageContentView(Panel parentPanel) {
		setParent(parentPanel);
		itemsPanel.setStyleName(styleNames.QP_PAGE_CONTENT());
		titlePanel.setStyleName(styleNames.QP_PAGE_TITLE());
	}

	private void setParent(Panel panel){
		pagePanel = panel;
		pagePanel.add(itemsPanel);
	}

	public Panel getPagePanel() {
		return pagePanel;
	}

	public Panel getItemsPanel() {
		return itemsPanel;
	}

	public Panel getTitlePanel() {
		return titlePanel;
	}

	public void setParentPanel(Panel parent){
		setParent(parent);
	}
}
