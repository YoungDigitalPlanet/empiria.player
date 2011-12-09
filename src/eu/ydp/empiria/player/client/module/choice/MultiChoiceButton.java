package eu.ydp.empiria.player.client.module.choice;

import com.google.gwt.user.client.ui.FlowPanel;

public class MultiChoiceButton extends ChoiceButtonBase {

	
	public MultiChoiceButton(){
		super();
		selected = false;
		updateStyle();
	}
	
	protected void updateStyle(){
		if (selected){
			setStyleName("qp-choice-button-single-selected");
		} else {
			setStyleName("qp-choice-button-single-notselected");
		}
	}
	
}
