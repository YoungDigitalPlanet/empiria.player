package eu.ydp.empiria.player.client.module.components.choicebutton;

import com.google.gwt.user.client.ui.PushButton;

public abstract class ChoiceButtonBase extends PushButton implements ISelectableButton {

	protected boolean selected;
	protected boolean over;
	protected String moduleStyleNamePart;

	public ChoiceButtonBase(String moduleStyleNamePart){
		selected = false;
		over = false;
		this.moduleStyleNamePart = moduleStyleNamePart;
	}
	
	public void setButtonEnabled(boolean value){
		setEnabled(value);
		updateStyle();
	}

	public void setSelected(boolean value){
		selected = value;
		updateStyle();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setMouseOver(boolean o){
		over = o;
		updateStyle();
	}
	
	public void select(){
		setSelected(true);
	}
	
	public void unselect(){
		setSelected(false);
	}
	
	protected void updateStyle(){
		String styleName = findStyleName();
		setStyleName(styleName);
	}
	
	protected String findStyleName(){
		String styleName = "qp-" + moduleStyleNamePart + "-button";
		if (selected){
			styleName += "-selected";
		} else {
			styleName += "-notselected";			
		}
		if (!isEnabled()){
			styleName += "-disabled";
		}
		if (over){
			styleName += "-over";
		}
		return styleName;
	}
}
