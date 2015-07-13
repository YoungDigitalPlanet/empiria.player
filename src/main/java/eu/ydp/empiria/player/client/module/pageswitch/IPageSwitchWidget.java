package eu.ydp.empiria.player.client.module.pageswitch;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public interface IPageSwitchWidget {

    public HandlerRegistration addChangeHandler(ChangeHandler handler);

    public void setItemsCount(Integer itemsCount);

    public Integer getCurrentIndex();

    public void setCurrentIndex(Integer value);

    public void enable();

    public void disable();

}
