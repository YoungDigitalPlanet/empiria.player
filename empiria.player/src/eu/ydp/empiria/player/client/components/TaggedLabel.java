package eu.ydp.empiria.player.client.components;

import com.google.gwt.user.client.ui.Label;

public class TaggedLabel extends Label {
	
	public TaggedLabel(String text, String _tag){
		super(text);
		tag = _tag;
	}
	
	private String tag;
	
	public String getTag(){
		return tag;
	}
}
