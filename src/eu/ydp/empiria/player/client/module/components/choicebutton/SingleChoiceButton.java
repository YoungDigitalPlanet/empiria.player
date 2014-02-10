package eu.ydp.empiria.player.client.module.components.choicebutton;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SingleChoiceButton extends ChoiceButtonBase {

	@Inject
	public SingleChoiceButton(@Assisted String moduleStyleNamePart) {
		super(moduleStyleNamePart);
		this.moduleStyleNamePart = moduleStyleNamePart;
		updateStyle();
	}

	@Override
	public void setSelected(boolean value) {
		super.setSelected(value);
	}

}
