package eu.ydp.empiria.player.client.module.core.base;

import java.util.List;

public interface IModule extends HasParent {
    @Deprecated
    List<IModule> getChildren();
}
