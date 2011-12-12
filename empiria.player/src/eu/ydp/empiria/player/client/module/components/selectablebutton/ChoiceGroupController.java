package eu.ydp.empiria.player.client.module.components.selectablebutton;

import java.util.ArrayList;
import java.util.List;

public class ChoiceGroupController {

	protected List<ISelectableButton> buttons;
	
	public ChoiceGroupController(){
		buttons = new ArrayList<ISelectableButton>();
	}
	
	public void addButton(ISelectableButton btn){
		buttons.add(btn);
	}
	
	public void unselectAll(){
		for (ISelectableButton currBtn : buttons){
			currBtn.unselect();
		}
	}
	
}
