package eu.ydp.empiria.player.client.controller.body.parenthood;

import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;

public interface ParenthoodGeneratorSocket {

    public void addChild(IModule child);

    public void pushParent(HasChildren parent);

    public void popParent();

}
