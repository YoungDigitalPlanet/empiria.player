package eu.ydp.empiria.player.client.module.core.base;

import eu.ydp.empiria.player.client.module.ModuleSocket;

import java.util.List;

public abstract class ParentedModuleBase implements IModule {

    private ModuleSocket moduleSocket;

    protected void initModule(ModuleSocket moduleSocket) {
        this.moduleSocket = moduleSocket;
    }

    protected ModuleSocket getModuleSocket() {
        return moduleSocket;
    }

    @Override
    public HasChildren getParentModule() {
        return moduleSocket.getParent(this);
    }

    @Override
    public List<IModule> getChildren() {
        return moduleSocket.getChildren(this);
    }

    @Override
    public List<HasChildren> getNestedParents() {
        return moduleSocket.getNestedParents(this);
    }
}
