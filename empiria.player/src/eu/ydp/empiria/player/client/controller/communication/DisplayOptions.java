package eu.ydp.empiria.player.client.controller.communication;

import com.google.gwt.core.client.JavaScriptObject;

public class DisplayOptions extends DisplayContentOptions {
	
	public DisplayOptions(){
		super();
		//previewMode = false;
	}

	public DisplayOptions(String[] tags){
		tagsIgnored = tags;
		//previewMode = false;
	}

	public static DisplayOptions fromJsObject(JavaScriptObject o){

		if (o == null)
			return new DisplayOptions();
		
		String[] tags2Ignore = new String[decodeDisplayOptionsObjectArrayLength(o)];
		for (int i = 0 ; i < decodeDisplayOptionsObjectArrayLength(o) ; i ++){
			tags2Ignore[i] = decodeDisplayOptionsObjectArrayItem(o, i);
		}
		
		return new DisplayOptions(tags2Ignore);
	}
	private native static int decodeDisplayOptionsObjectArrayLength(JavaScriptObject obj)/*-{
		return obj.length;
	}-*/;

	private native static String decodeDisplayOptionsObjectArrayItem(JavaScriptObject obj, int index)/*-{
		return obj[index];
	}-*/;
}
