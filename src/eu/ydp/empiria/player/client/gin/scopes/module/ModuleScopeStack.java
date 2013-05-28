package eu.ydp.empiria.player.client.gin.scopes.module;

import java.util.Stack;

import com.google.inject.Singleton;

@Singleton
public class ModuleScopeStack {

	private Stack<ModuleCreationContext> contextsStack = new Stack<ModuleCreationContext>();
	
	public void pushContext(ModuleCreationContext context){
		contextsStack.push(context);
	}
	
	public ModuleCreationContext getCurrentTopContext(){
		ModuleCreationContext context = contextsStack.peek();
		return context;
	}
	
	public ModuleCreationContext pop(){
		return contextsStack.pop();
	}
}
