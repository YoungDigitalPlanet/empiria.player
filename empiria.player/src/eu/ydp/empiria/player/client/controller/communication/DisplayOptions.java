package eu.ydp.empiria.player.client.controller.communication;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

public class DisplayOptions extends DisplayContentOptions {
	
	public DisplayOptions(){
		super();
	}

	public DisplayOptions(List<String> tags){
		ignoredTags = tags;
	}

	public static DisplayOptions fromJsObject(JavaScriptObject o){

		if (o == null)
			return new DisplayOptions();
		
		JavaScriptObject is = decodeDisplayOptionsObjectGetIgnoredSections(o);
		
		List<String> tags2Ignore = new ArrayList<String>(); 
		int arrayLength = decodeDisplayOptionsObjectArrayLength(is);
		for (int i = 0 ; i < arrayLength ; i ++){
			tags2Ignore.add( decodeDisplayOptionsObjectArrayItem(is, i) );
		}
		
		return new DisplayOptions(tags2Ignore);
	}
	private native static JavaScriptObject decodeDisplayOptionsObjectGetIgnoredSections(JavaScriptObject obj)/*-{
		if (typeof obj.ignoredSections == 'object'){
			return obj.ignoredSections;
		}
		return [];
	}-*/;

	private native static int decodeDisplayOptionsObjectArrayLength(JavaScriptObject obj)/*-{
		return obj.length;
	}-*/;

	private native static String decodeDisplayOptionsObjectArrayItem(JavaScriptObject obj, int index)/*-{
		return obj[index];
	}-*/;
}
