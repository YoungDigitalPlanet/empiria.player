package eu.ydp.empiria.player.client.controller.body.parenthood;

import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.HasParent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedParenthoodManager {

    private Map<HasParent, List<HasChildren>> nestedParents = Maps.newHashMap();
    private Map<HasChildren, List<HasParent>> nestedChildren = Maps.newHashMap();

    public void addParent(HasChildren parent) {
        if (!nestedChildren.containsKey(parent)) {
            nestedChildren.put(parent, new ArrayList<HasParent>());
        }
    }

    public void addChildToParent(HasParent child, HasChildren parent) {
        nestedParents.put(child, new ArrayList<HasChildren>());
        nestedParents.get(child).add(parent);

        nestedChildren.get(parent).add(child);

        if (nestedParents.containsKey(parent)) {
            List<HasChildren> grandParents = nestedParents.get(parent);
            nestedParents.get(child).addAll(grandParents);

            for (HasChildren grandParent : grandParents) {
                nestedChildren.get(grandParent).add(child);
            }
        }
    }

    public List<HasChildren> getNestedParents(HasParent child) {
        return nestedParents.get(child);
    }

    public List<HasParent> getNestedChildren(HasChildren parent) {
        return nestedChildren.get(parent);
    }
}
