package eu.ydp.empiria.player.client.module;

import java.util.List;

public abstract class ParentedModuleBase implements IModule {

	private ModuleSocket moduleSocket;

	protected void initModule(ModuleSocket moduleSocket){
		this.moduleSocket = moduleSocket;
	}

	protected ModuleSocket getModuleSocket() {
		return moduleSocket;
	}

	@Override
	public final HasChildren getParentModule() {
		return moduleSocket.getParent(this);
	}

	@Override
	public List<IModule> getChildren() {
		return moduleSocket.getChildren(this);
	}
}
