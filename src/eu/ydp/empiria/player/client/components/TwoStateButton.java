package eu.ydp.empiria.player.client.components;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;

public class TwoStateButton extends PushButton {

	protected boolean stateDown = false;
	protected String upStyleName;
	protected String downStyleName;
	
	public TwoStateButton(String upStyleName, String downStyleName){
		super();
		
		this.upStyleName = upStyleName;
		this.downStyleName = downStyleName;
		updateStyleName();
		
		addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				stateDown = !stateDown;
				updateStyleName();
			}
		});
	}
	
	protected void updateStyleName(){
		if (stateDown){
			setStylePrimaryName(downStyleName);
		} else {
			setStylePrimaryName(upStyleName);
		}
	}
	
	public boolean isStateDown(){
		return stateDown;
	}
	
	public void setStateDown(boolean d){
		stateDown = d;
		updateStyleName();
	}
}
