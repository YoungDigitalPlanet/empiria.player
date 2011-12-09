package eu.ydp.empiria.player.client.module.choice;

import java.util.ArrayList;
import java.util.List;

public class ChoiceGroupController {

	protected List<SingleChoiceButton> buttons;
	
	public ChoiceGroupController(){
		buttons = new ArrayList<SingleChoiceButton>();
	}
	
	public void addButton(SingleChoiceButton btn){
		buttons.add(btn);
	}
	
	public void unselectAll(){
		for (SingleChoiceButton currBtn : buttons){
			currBtn.unselect();
		}
	}
	
}
