package eu.ydp.empiria.player.client.module.components.choicebutton;

import com.google.inject.Inject;


public class MultiChoiceButton extends ChoiceButtonBase {

	@Inject
	public MultiChoiceButton(String moduleStyleNamePart){
		
		super(moduleStyleNamePart);
		this.moduleStyleNamePart = moduleStyleNamePart;
		selected = false;
		updateStyle();
	}
	
}
