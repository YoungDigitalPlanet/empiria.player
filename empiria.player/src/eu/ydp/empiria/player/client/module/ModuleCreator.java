package eu.ydp.empiria.player.client.module;

public interface ModuleCreator {

	public IModule createModule();
	
	public boolean isMultiViewModule();
	
	public boolean isInlineModule();
}
