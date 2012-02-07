package eu.ydp.empiria.player.client.controller.data.library;

public class LibraryInternalExtension implements LibraryExtension {

	protected String name;
	
	public LibraryInternalExtension(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
