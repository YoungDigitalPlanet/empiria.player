package eu.ydp.empiria.player.client.module.pageswitch;

import com.google.gwt.user.client.ui.ListBox;


public class PageSwitchListBox extends ListBox implements IPageSwitchWidget{
	
	@Override
	public void setItemsCount(Integer itemsCount) {
		initializeList(itemsCount);
	}
	
	@Override
	public void setCurrentIndex(Integer value) {
		setSelectedIndex(value);
	}
	
	@Override
	public Integer getCurrentIndex() {
		return getSelectedIndex();
	}
	
	private void initializeList(Integer itemsNum){
		Integer itemIndex;
		
		for(Integer i = 0; i < itemsNum; i++){
			itemIndex = i + 1;
			addItem(itemIndex.toString());
		}
	}
}
