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
	
	protected boolean selected = false;
	protected boolean over = false;
	
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
				setOver(true);				
			}
		}, MouseOverEvent.getType());
		popupPanel.addDomHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent arg0) {
				setOver(false);
				
			}
		}, MouseOutEvent.getType());
		popupPanel.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setOver(false);
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
	
	protected void setOver(boolean ov){
		over = ov;
		updateStyle();
	}
		
	public void setSelected(boolean sel){
		selected = sel;
		updateStyle();
	}
	
	protected void updateStyle(){
		String selectedPart = "";
		if (selected)
			selectedPart = "-selected";
		String overPart = "";
		if (over)
			overPart = "-over";
		popupPanel.setStyleName("qp-exlistbox-popup-option-panel"+selectedPart+overPart);
		if (selected)
			popupButton.setStyleName("qp-exlistbox-popup-option-button-selected");
		else
			popupButton.setStyleName("qp-exlistbox-popup-option-button");
	}
}
