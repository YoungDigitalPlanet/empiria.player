package eu.ydp.empiria.player.client.controller.item;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

@Singleton
public class PageScopedItemXMLWrapperProvider extends PageScopedProvider<ItemXMLWrapper> {

    @Inject
    public PageScopedItemXMLWrapperProvider(Provider<ItemXMLWrapper> provider, CurrentPageScopeProvider currentScopeProvider) {
        super(provider, currentScopeProvider);
    }

}
