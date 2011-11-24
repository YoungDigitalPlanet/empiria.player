package eu.ydp.empiria.player.client.controller.communication;

import java.util.Vector;

public class DisplayContentOptions {
	
	public DisplayContentOptions(){
		tagsIgnored = new String[0];
	}

	protected String[] tagsIgnored;

	public String[] getIgnoredTags(){
		return tagsIgnored;
	}

	public Vector<String> getIgnoredTagsAsVector(){
		Vector<String> v = new Vector<String>();
		for (String tag:tagsIgnored)
			v.add(tag);
		return v;
	}

}
