package eu.ydp.empiria.player.client.view.item;

import com.google.gwt.user.client.ui.Panel;

public class ItemContentView implements ItemViewSocket {
	
	public ItemContentView(Panel p){
		itemPanel = p;
	}
	
	private Panel itemPanel;

	@Override
	public void setItemView(ItemViewCarrier ivc) {
		itemPanel.clear();
		if (!ivc.isError()){
			itemPanel.add(ivc.getTitleView());
			itemPanel.add(ivc.getContentView());
			itemPanel.add(ivc.getFeedbackView());
			itemPanel.add(ivc.getScoreView());
		} else {
			itemPanel.add(ivc.getErrorView());
		}
	}

}
