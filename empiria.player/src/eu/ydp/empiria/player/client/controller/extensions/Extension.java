package eu.ydp.empiria.player.client.controller.extensions;

public abstract class Extension {
	
	public abstract ExtensionType getType();
	
	public abstract void init();
}
