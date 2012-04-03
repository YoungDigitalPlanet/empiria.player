package eu.ydp.empiria.player.client.controller.body;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ISingleViewWithBodyModule;
import eu.ydp.empiria.player.client.util.StackMap;

public class ParenthoodManager implements ParenthoodSocket {

	protected StackMap<ISingleViewWithBodyModule, List<IModule>> parenthood;
	protected Stack<ISingleViewWithBodyModule> parentsStack;
	
	public ParenthoodManager(){
		parenthood = new StackMap<ISingleViewWithBodyModule, List<IModule>>();
		parentsStack = new Stack<ISingleViewWithBodyModule>();
	}
	
	/**
	 * Add child to parent's children list
	 */
	@Override
	public void addChild(IModule child){
		addChildToMap(child);
	}

	protected void addChildToMap(IModule child){
		ISingleViewWithBodyModule parent = parentsStack.peek();
		if (!parenthood.containsKey(parent)){
			parenthood.put(parent, new ArrayList<IModule>());
		}
		parenthood.get(parent).add(child);
	}
	
	public ISingleViewWithBodyModule getParent(IModule child){
		for (ISingleViewWithBodyModule parent : parenthood.keySet()){
			if (parenthood.get(parent).contains(child)){
				return parent;
			}
		}
		return null;
	}

	public List<IModule> getChildren(IModule parent) {
		if (parent instanceof ISingleViewWithBodyModule){
			if (parenthood.containsKey((ISingleViewWithBodyModule)parent))
				return parenthood.get((ISingleViewWithBodyModule)parent);
		}
		return new ArrayList<IModule>();
	}

	@Override
	public void pushParent(ISingleViewWithBodyModule parent) {
		parentsStack.push(parent);
	}

	@Override
	public void popParent() {
		parentsStack.pop();
	}
}
