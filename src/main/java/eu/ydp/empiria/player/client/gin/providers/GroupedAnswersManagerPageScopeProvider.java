package eu.ydp.empiria.player.client.gin.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

public class GroupedAnswersManagerPageScopeProvider extends PageScopedProvider<GroupedAnswersManager> {

    @Inject
    public GroupedAnswersManagerPageScopeProvider(Provider<GroupedAnswersManager> provider, CurrentPageScopeProvider currentScopeProvider) {
        super(provider, currentScopeProvider);
    }

}
