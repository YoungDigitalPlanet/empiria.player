package eu.ydp.empiria.player.client.module.components.choicebutton;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


public class MultiChoiceButton extends ChoiceButtonBase {

	@Inject
	public MultiChoiceButton(@Assisted String moduleStyleNamePart){
		
		super(moduleStyleNamePart);
		this.moduleStyleNamePart = moduleStyleNamePart;
		selected = false;
		updateStyle();
	}
	
}
