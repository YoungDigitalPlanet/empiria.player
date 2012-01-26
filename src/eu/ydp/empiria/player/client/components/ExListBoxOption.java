package eu.ydp.empiria.player.client.components;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class ExListBoxOption {

	protected Widget baseBody;
	protected Widget popupBody;
	protected Panel popupPanel;
	protected Widget popupButton;
	
	public ExListBoxOption(Widget baseBody, Widget popupBody){
		this.baseBody = baseBody;
		this.popupBody = popupBody;
		
		popupButton = new PushButton();
		popupButton.setStyleName("qp-exlistbox-popup-option-button");				
		
		popupPanel = new FlowPanel();
		popupPanel.setStyleName("qp-exlistbox-popup-option-panel");
		popupPanel.addDomHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent arg0) {
				popupPanel.setStyleName("qp-exlistbox-popup-option-panel-over");				
			}
		}, MouseOverEvent.getType());
		popupPanel.addDomHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent arg0) {
				popupPanel.setStyleName("qp-exlistbox-popup-option-panel");
				
			}
		}, MouseOutEvent.getType());
		popupPanel.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				popupPanel.setStyleName("qp-exlistbox-popup-option-panel");
			}
		}, ClickEvent.getType());
		popupPanel.add(popupBody);
		popupPanel.add(popupButton);
		
	}
	
	public Widget getPopupBody(){
		return popupPanel;
	}
	
	public Widget getBaseBody(){
		return baseBody;
	}
		
	public void setSelected(boolean sel){
		if (sel)
			popupButton.setStyleName("qp-exlistbox-popup-option-button-selected");
		else
			popupButton.setStyleName("qp-exlistbox-popup-option-button");
	}
}
