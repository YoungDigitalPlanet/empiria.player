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
			if(ivc.getTitleView() != null)
				itemPanel.add(ivc.getTitleView());
			
			if(ivc.getContentView() != null)
				itemPanel.add(ivc.getContentView());
			
			if(ivc.getFeedbackView() != null)
				itemPanel.add(ivc.getFeedbackView());
			
			if(ivc.getScoreView() != null)
				itemPanel.add(ivc.getScoreView());
		} else {
			itemPanel.add(ivc.getErrorView());
		}
	}

}
