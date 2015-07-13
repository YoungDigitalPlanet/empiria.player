package eu.ydp.empiria.player.client.controller.body.parenthood;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.HasParent;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.gwtutil.client.collections.StackMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ParenthoodManager implements ParenthoodGeneratorSocket {

    protected StackMap<HasChildren, List<IModule>> parenthood;
    protected Stack<HasChildren> parentsStack;
    protected ParenthoodSocket upperLevelParenthoodSocket;
    protected Map<IModule, HasChildren> childToParent = Maps.newHashMap();

    @Inject
    private NestedParenthoodManager nestedParenthoodManager;

    public ParenthoodManager() {
        parenthood = new StackMap<HasChildren, List<IModule>>();
        parentsStack = new Stack<HasChildren>();
    }

    @Override
    public void addChild(IModule child) {
        addChildToMap(child);
    }

    protected void addChildToMap(IModule child) {
        HasChildren parent = parentsStack.peek();
        if (!parenthood.containsKey(parent)) {
            parenthood.put(parent, new ArrayList<IModule>());
        }
        parenthood.get(parent).add(child);
        nestedParenthoodManager.addChildToParent(child, parent);
    }

    @Override
    public void pushParent(HasChildren parent) {
        parentsStack.push(parent);
        nestedParenthoodManager.addParent(parent);
    }

    @Override
    public void popParent() {
        parentsStack.pop();
    }

    public List<HasChildren> getNestedParents(HasParent child){
        return nestedParenthoodManager.getNestedParents(child);
    }

    public List<HasParent> getNestedChildren(HasChildren parent){
        return nestedParenthoodManager.getNestedChildren(parent);
    }

    public HasChildren getParent(IModule child) {
        if (childToParent.containsKey(child)) {
            return childToParent.get(child);
        }

        HasChildren hasChildren;
        hasChildren = findParentInParentHood(child);
        if (hasChildren == null) {
            hasChildren = findParentInUpperLevelParentHoodSocket(child);
        }

        addChildToParentRelation(child, hasChildren);
        return hasChildren;
    }

    private void addChildToParentRelation(IModule child, HasChildren hasChildren) {
        if (hasChildren != null) {
            childToParent.put(child, hasChildren);
        }
    }

    private HasChildren findParentInUpperLevelParentHoodSocket(IModule child) {
        if (upperLevelParenthoodSocket != null) {
            return upperLevelParenthoodSocket.getParent(child);
        }
        return null;
    }

    private HasChildren findParentInParentHood(IModule child) {
        for (HasChildren parent : parenthood.keySet()) {
            if (parenthood.get(parent).contains(child)) {
                return parent;
            }
        }
        return null;
    }

    public List<IModule> getChildren(IModule parent) {
        if (parent instanceof HasChildren) {
            if (parenthood.containsKey(parent)) {
                return parenthood.get(parent);
            } else if (upperLevelParenthoodSocket != null) {
                return upperLevelParenthoodSocket.getChildren(parent);
            }
        }
        return new ArrayList<IModule>();
    }

    public void setUpperLevelParenthood(ParenthoodSocket ps) {
        upperLevelParenthoodSocket = ps;
    }
}
