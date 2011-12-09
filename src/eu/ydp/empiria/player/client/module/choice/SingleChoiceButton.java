package eu.ydp.empiria.player.client.module.choice;

public class SingleChoiceButton extends ChoiceButtonBase {

	protected ChoiceGroupController ctrl;
	
	public SingleChoiceButton(ChoiceGroupController ctrl){
		super();
		this.ctrl = ctrl;
		ctrl.addButton(this);
		updateStyle();
	}
	
	@Override
	protected void updateStyle() {

		if (selected){
			addStyleName("qp-choice-button-multi-selected");
			removeStyleName("qp-choice-button-multi-notselected");
		} else {
			addStyleName("qp-choice-button-multi-notselected");
			removeStyleName("qp-choice-button-multi-selected");
		}
	}
	
	@Override
	public void setSelected(boolean value){
		if (!selected  &&  value){
			ctrl.unselectAll();
			super.setSelected(value);
		} else if (selected  &&  !value){
			super.setSelected(value);
		}
	}
	
	public void unselect(){
		super.setSelected(false);
	}
	

}
