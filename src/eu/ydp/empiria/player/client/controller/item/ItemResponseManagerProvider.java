package eu.ydp.empiria.player.client.controller.item;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseNodeParser;

public class ItemResponseManagerProvider implements Provider<ItemResponseManager> {

	@Inject
	private Provider<ItemXMLWrapper> xmlMapperProvider;

	@Inject
	private ResponseNodeParser nodeParser;

	public ItemResponseManager getResponseManager() {
		return new ItemResponseManager(xmlMapperProvider.get().getResponseDeclarations(), nodeParser);
	}

	@Override
	public ItemResponseManager get() {
		return getResponseManager();
	}
}
