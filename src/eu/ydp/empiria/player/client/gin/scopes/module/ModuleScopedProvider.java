package eu.ydp.empiria.player.client.gin.scopes.module;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.gin.scopes.CustomScopedProvider;

public class ModuleScopedProvider<T> extends CustomScopedProvider<T>{

	@Inject
	public ModuleScopedProvider(Provider<T> provider, CurrentModuleScopeProvider currentScopeProvider) {
		super(provider, currentScopeProvider);
	}

}
