package eu.ydp.empiria.player.client.controller.data.library;

import com.google.gwt.core.client.JavaScriptObject;

public class LibraryExternalExtension implements LibraryExtension {

	protected JavaScriptObject extCreator;
	
	public LibraryExternalExtension(JavaScriptObject extCreator){
		this.extCreator = extCreator;
	}
	
	public JavaScriptObject getExtensionInstance(){
		return getInstanceJs(extCreator);
	}
	
	private native JavaScriptObject getInstanceJs(JavaScriptObject extCreator)/*-{
		if (typeof extCreator.create == 'function'){
			return extCreator.create();
		}
		return null;
	}-*/;
}
