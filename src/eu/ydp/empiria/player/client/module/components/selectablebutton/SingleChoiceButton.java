package eu.ydp.empiria.player.client.module.components.selectablebutton;

public class SingleChoiceButton extends ChoiceButtonBase {

	protected ChoiceGroupController ctrl;
	
	public SingleChoiceButton(ChoiceGroupController ctrl, String moduleStyleNamePart){
		super(moduleStyleNamePart);
		this.ctrl = ctrl;
		this.moduleStyleNamePart = moduleStyleNamePart;
		ctrl.addButton(this);
		updateStyle();
	}
	
	@Override
	protected void updateStyle() {

		if (selected){
			addStyleName("qp-"+moduleStyleNamePart+"-button-single-selected");
			removeStyleName("qp-"+moduleStyleNamePart+"-button-single-notselected");
		} else {
			addStyleName("qp-"+moduleStyleNamePart+"-button-single-notselected");
			removeStyleName("qp-"+moduleStyleNamePart+"-button-single-selected");
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
	

}
