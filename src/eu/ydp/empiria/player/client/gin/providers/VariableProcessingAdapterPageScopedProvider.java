package eu.ydp.empiria.player.client.gin.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingAdapter;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

public class VariableProcessingAdapterPageScopedProvider extends PageScopedProvider<VariableProcessingAdapter> {

	@Inject
	public VariableProcessingAdapterPageScopedProvider(Provider<VariableProcessingAdapter> provider, CurrentPageScopeProvider currentScopeProvider) {
		super(provider, currentScopeProvider);
	}

}
