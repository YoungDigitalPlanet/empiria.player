package eu.ydp.empiria.player.client.module.components.choicebutton;

import com.google.gwt.user.client.ui.FlowPanel;

public class MultiChoiceButton extends ChoiceButtonBase {

	
	public MultiChoiceButton(String moduleStyleNamePart){
		super(moduleStyleNamePart);
		this.moduleStyleNamePart = moduleStyleNamePart;
		selected = false;
		updateStyle();
	}
	
}
