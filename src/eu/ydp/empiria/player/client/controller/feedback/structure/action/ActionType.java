package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import java.util.Locale;

public enum ActionType {
	NARRATION, VIDEO, POPUP;
	
	public boolean equalsToString(String value){
		boolean equals = false;
		
		for(ActionType type: values()){
			if(type.getName().equals(value)){
				equals = true;
				break;
			}
		}
		
		return equals;
	}
	
	public String getName(){
		return toString().toLowerCase(Locale.ENGLISH);
	}
}
