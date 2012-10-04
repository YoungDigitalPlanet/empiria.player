package eu.ydp.empiria.player.client.module;

import java.util.List;
import java.util.Stack;

import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public interface ParenthoodSocket {

	public IModule getParent(IModule module);
	public GroupIdentifier getParentGroupIdentifier(IModule module);
	public List<IModule> getChildren(IModule parent);
	Stack<IModule> getParentsHierarchy(IModule module);
}
