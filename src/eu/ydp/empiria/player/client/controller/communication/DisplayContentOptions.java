package eu.ydp.empiria.player.client.controller.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class DisplayContentOptions {
	
	public DisplayContentOptions(){
		ignoredTags = new ArrayList<String>();
		ignoredInlineTags = new ArrayList<String>();
		ignoredInlineTags.add("feedbackInline");
	}

	protected List<String> ignoredTags;
	protected List<String> ignoredInlineTags;
	
	public List<String> getIgnoredTags(){
		return ignoredTags;
	}

	public List<String> getIgnoredInlineTags(){
		return ignoredInlineTags;
	}

}
