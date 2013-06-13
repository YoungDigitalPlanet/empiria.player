package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScopeStack;

public class ModuleScopedLazyProvider<T> {

	private Provider<T> instanceProvider;
	private ModuleScopeStack moduleScopeStack;
	private ModuleCreationContext currentTopContext;

	@Inject
	public ModuleScopedLazyProvider(Provider<T> instanceProvider, ModuleScopeStack moduleScopeStack) {
		this.instanceProvider = instanceProvider;
		this.moduleScopeStack = moduleScopeStack;

		currentTopContext = moduleScopeStack.getCurrentTopContext();
	}

	public T get() {
		moduleScopeStack.pushContext(currentTopContext);
		T instance = instanceProvider.get();
		moduleScopeStack.pop();
		return instance;
	}
}
