package eu.ydp.empiria.player.client.gin.scopes.module;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.gin.scopes.AbstractCustomScope;
import eu.ydp.gwtutil.client.gin.scopes.CurrentScopeProvider;

public class CurrentModuleScopeProvider implements CurrentScopeProvider {

	private final ModuleScopeStack moduleScopeStack;
	
	@Inject
	public CurrentModuleScopeProvider(ModuleScopeStack moduleScopeStack) {
		this.moduleScopeStack = moduleScopeStack;
	}

	@Override
	public AbstractCustomScope getCurrentScope() {
		ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
		ModuleScope moduleScope = new ModuleScope(context);
		return moduleScope;
	}

}
