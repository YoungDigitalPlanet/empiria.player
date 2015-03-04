package eu.ydp.empiria.player.client.gin.scopes.page;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.gin.scopes.CustomScopedProvider;

public class PageScopedProvider<T> extends CustomScopedProvider<T> {

	@Inject
	public PageScopedProvider(Provider<T> provider, CurrentPageScopeProvider currentScopeProvider) {
		super(provider, currentScopeProvider);
	}

}
