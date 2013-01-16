package eu.ydp.empiria.player.client.controller.body;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.gwtutil.client.collections.StackMap;

public class ParenthoodManager implements ParenthoodGeneratorSocket {

	protected StackMap<HasChildren, List<IModule>> parenthood;
	protected Stack<HasChildren> parentsStack;
	protected ParenthoodSocket upperLevelParenthoodSocket;
	
	public ParenthoodManager(){
		parenthood = new StackMap<HasChildren, List<IModule>>();
		parentsStack = new Stack<HasChildren>();
	}
	
	@Override
	public void addChild(IModule child){
		addChildToMap(child);
	}

	protected void addChildToMap(IModule child){
		HasChildren parent = parentsStack.peek();
		if (!parenthood.containsKey(parent)){
			parenthood.put(parent, new ArrayList<IModule>());
		}
		parenthood.get(parent).add(child);
	}

	@Override
	public void pushParent(HasChildren parent) {
		parentsStack.push(parent);
	}

	@Override
	public void popParent() {
		parentsStack.pop();
	}
	
	public HasChildren getParent(IModule child){
		for (HasChildren parent : parenthood.keySet()){
			if (parenthood.get(parent).contains(child)){
				return parent;
			}
		}
		if (upperLevelParenthoodSocket != null){
			return upperLevelParenthoodSocket.getParent(child);
		}
		return null;
	}

	public List<IModule> getChildren(IModule parent) {
		if (parent instanceof HasChildren){
			if (parenthood.containsKey((HasChildren)parent))
				return parenthood.get((HasChildren)parent);
			else if (upperLevelParenthoodSocket != null)
				return upperLevelParenthoodSocket.getChildren((HasChildren)parent);
		}
		return new ArrayList<IModule>();
	}

	public void setUpperLevelParenthood(ParenthoodSocket ps) {
		upperLevelParenthoodSocket = ps;
	}
}
