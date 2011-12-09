package eu.ydp.empiria.player.client.module.choice;

import com.google.gwt.user.client.ui.FlowPanel;

public abstract class ChoiceButtonBase extends FlowPanel {

	protected boolean selected;
	protected boolean enabled;
	protected boolean over;

	public ChoiceButtonBase(){
		selected = false;
		enabled = true;
		over = false;
	}
	
	abstract protected void updateStyle();
	

	public void markAsCorrect(boolean mark) {
		if (mark){
			addStyleName("qp-choice-button-correct");
		} else {
			removeStyleName("qp-choice-button-correct");
		}
	}

	public void markAsWrong(boolean mark) {
		if (mark){
			addStyleName("qp-choice-button-wrong");
		} else {
			removeStyleName("qp-choice-button-wrong");
		}
	}
	
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		if (enabled){
			removeStyleName("qp-choice-button-disabled");
		} else {
			addStyleName("qp-choice-button-disabled");
		}
	}

	public void setSelected(boolean value){
		selected = value;
		updateStyle();
	}
	
	public boolean getSelected(){
		return selected;
	}
	
	public void setMouseOver(boolean o){
		if (o  &&  !over){
			addStyleName("qp-choice-button-over");
		} else if (!o){
			removeStyleName("qp-choice-button-over");
		}
	}
}
