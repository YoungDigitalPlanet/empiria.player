package eu.ydp.empiria.player.client.module.components.selectablebutton;

import com.google.gwt.user.client.ui.FlowPanel;

public abstract class ChoiceButtonBase extends FlowPanel implements ISelectableButton {

	protected boolean selected;
	protected boolean enabled;
	protected boolean over;
	protected String moduleStyleNamePart;

	public ChoiceButtonBase(String moduleStyleNamePart){
		selected = false;
		enabled = true;
		over = false;
		this.moduleStyleNamePart = moduleStyleNamePart;
	}
	
	abstract protected void updateStyle();
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		if (enabled){
			removeStyleName("qp-"+moduleStyleNamePart+"-button-disabled");
		} else {
			addStyleName("qp-"+moduleStyleNamePart+"-button-disabled");
		}
	}

	public void setSelected(boolean value){
		selected = value;
		updateStyle();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setMouseOver(boolean o){
		if (o  &&  !over){
			addStyleName("qp-"+moduleStyleNamePart+"-button-over");
		} else if (!o){
			removeStyleName("qp-"+moduleStyleNamePart+"-button-over");
		}
	}
	
	public void select(){
		setSelected(true);
	}
	
	public void unselect(){
		setSelected(false);
	}
}
