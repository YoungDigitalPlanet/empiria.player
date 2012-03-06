package eu.ydp.empiria.player.client.module.test.navigation.pagesswitch;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public interface IPagesSwitchWidget {
	
	public HandlerRegistration addChangeHandler(ChangeHandler handler);
	
	public void setItemsCount(Integer itemsCount);
	
	public Integer getCurrentIndex();
	
	public void setCurrentIndex(Integer value);
	
	
}
