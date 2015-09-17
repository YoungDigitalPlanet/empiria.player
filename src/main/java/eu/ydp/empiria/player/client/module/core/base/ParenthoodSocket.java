package eu.ydp.empiria.player.client.module.core.base;

import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

import java.util.List;
import java.util.Set;
import java.util.Stack;

public interface ParenthoodSocket {

    HasChildren getParent(IModule module);

    GroupIdentifier getParentGroupIdentifier(IModule module);

    List<IModule> getChildren(IModule parent);

    List<HasParent> getNestedChildren(HasChildren parent);

    List<HasChildren> getNestedParents(HasParent child);

    Stack<HasChildren> getParentsHierarchy(IModule module);

    Set<InlineFormattingContainerType> getInlineFormattingTags(IModule module);
}
