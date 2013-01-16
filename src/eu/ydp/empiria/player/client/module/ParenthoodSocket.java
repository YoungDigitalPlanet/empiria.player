package eu.ydp.empiria.player.client.module;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public interface ParenthoodSocket {

	HasChildren getParent(IModule module);
	GroupIdentifier getParentGroupIdentifier(IModule module);
	List<IModule> getChildren(IModule parent);
	Stack<HasChildren> getParentsHierarchy(IModule module);
	public Set<InlineFormattingContainerType> getInlineFormattingTags( IModule module );
}
