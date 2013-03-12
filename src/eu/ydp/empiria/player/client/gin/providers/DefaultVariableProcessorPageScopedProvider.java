package eu.ydp.empiria.player.client.gin.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

public class DefaultVariableProcessorPageScopedProvider extends PageScopedProvider<DefaultVariableProcessor>{

	@Inject
	public DefaultVariableProcessorPageScopedProvider(Provider<DefaultVariableProcessor> provider, CurrentPageScopeProvider currentScopeProvider) {
		super(provider, currentScopeProvider);
	}

}
