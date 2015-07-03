package eu.ydp.empiria.player.client.module;

import java.util.List;

public interface HasChildren extends IModule {
    public List<IModule> getChildrenModules();
}
