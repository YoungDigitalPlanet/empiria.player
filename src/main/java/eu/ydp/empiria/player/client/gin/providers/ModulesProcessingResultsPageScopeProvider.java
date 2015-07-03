package eu.ydp.empiria.player.client.gin.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

public class ModulesProcessingResultsPageScopeProvider extends PageScopedProvider<ModulesProcessingResults> {

    @Inject
    public ModulesProcessingResultsPageScopeProvider(Provider<ModulesProcessingResults> provider, CurrentPageScopeProvider currentScopeProvider) {
        super(provider, currentScopeProvider);
    }

}
