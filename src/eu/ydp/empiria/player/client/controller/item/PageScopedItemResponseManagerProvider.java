package eu.ydp.empiria.player.client.controller.item;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseNodeParser;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

public class PageScopedItemResponseManagerProvider extends PageScopedProvider<ItemResponseManager> {

	@Inject
	public PageScopedItemResponseManagerProvider(Provider<ItemXMLWrapper> xmlMapperProvider, ResponseNodeParser nodeParser, CurrentPageScopeProvider currentScopeProvider) {
		super(new ItemResponseManagerProvider(xmlMapperProvider, nodeParser), currentScopeProvider);
	}

}
