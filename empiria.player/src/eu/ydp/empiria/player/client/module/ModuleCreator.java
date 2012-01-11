package eu.ydp.empiria.player.client.module;

public interface ModuleCreator {

	public IModule createModule();
	
	public boolean isInteractionModule();
	
	public boolean isInlineModule();
}
