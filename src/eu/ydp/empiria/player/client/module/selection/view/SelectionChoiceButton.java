package eu.ydp.empiria.player.client.module.selection.view;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

public class SelectionChoiceButton extends ChoiceButtonBase {

	@Inject
	public SelectionChoiceButton(
			@Assisted("moduleStyleNamePart") String moduleStyleNamePart) {
		super(moduleStyleNamePart);
		selected = false;
		updateStyle(); //NOPMD
	}
	
	@Override
	public void setSelected(boolean value){
		selected = value;
	}
	
	@Override
	public void setButtonEnabled(boolean value){
		setEnabled(value);
	}
	
	@Override
	public void updateStyle(){
		String styleName = findSelectedStyleName();
		setStyleName(styleName);
		
		String secondaryStyleName = findSecondaryStyleName();
		addStyleName(secondaryStyleName);
	}

	private String findSecondaryStyleName() {
		String styleName = "qp-" + moduleStyleNamePart + "-button";
		if (selected){
			styleName += "-selected";
		} else {
			styleName += "-notselected";			
		}
		if (!isEnabled()){
			styleName += "-up-disabled";
		}
		if (over){
			styleName += "-over";
		}
		return styleName;
	}

	protected String findSelectedStyleName(){
		String styleName = "qp-" + moduleStyleNamePart + "-button";
		if (selected){
			styleName += "-selected";
		} else {
			styleName += "-notselected";			
		}
		if (over){
			styleName += "-over";
		}
		return styleName;
	}
	
}
