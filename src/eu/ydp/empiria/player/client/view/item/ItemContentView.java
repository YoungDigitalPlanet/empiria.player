package eu.ydp.empiria.player.client.view.item;

import com.google.gwt.user.client.ui.Panel;

public class ItemContentView implements ItemViewSocket {
	private final Panel itemPanel;
	public ItemContentView(Panel parent){
		itemPanel = parent;
	}

	@Override
	public void setItemView(ItemViewCarrier ivc) {
		itemPanel.clear();
		if (ivc.isError()){
			itemPanel.add(ivc.getErrorView());
		} else {
			if(ivc.getTitleView() != null) {
				itemPanel.add(ivc.getTitleView());
			}

			if(ivc.getContentView() != null) {
				itemPanel.add(ivc.getContentView());
			}

			if(ivc.getScoreView() != null) {
				itemPanel.add(ivc.getScoreView());
			}

		}
	}

}
