package eu.ydp.empiria.player.client.module.components.selectablebutton;

import com.google.gwt.user.client.ui.FlowPanel;

public class MultiChoiceButton extends ChoiceButtonBase {

	
	public MultiChoiceButton(String moduleStyleNamePart){
		super(moduleStyleNamePart);
		this.moduleStyleNamePart = moduleStyleNamePart;
		selected = false;
		updateStyle();
	}
	
	protected void updateStyle(){
		if (selected){
			setStyleName("qp-"+moduleStyleNamePart+"-button-multi-selected");
		} else {
			setStyleName("qp-"+moduleStyleNamePart+"-button-multi-notselected");
		}
	}
	
}
