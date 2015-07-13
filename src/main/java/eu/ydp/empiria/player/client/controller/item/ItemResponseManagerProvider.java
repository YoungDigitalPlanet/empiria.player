package eu.ydp.empiria.player.client.controller.item;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseNodeParser;

public class ItemResponseManagerProvider implements Provider<ItemResponseManager> {

    private final Provider<ItemXMLWrapper> xmlMapperProvider;
    private final ResponseNodeParser nodeParser;

    public ItemResponseManagerProvider(Provider<ItemXMLWrapper> xmlMapperProvider, ResponseNodeParser nodeParser) {
        this.xmlMapperProvider = xmlMapperProvider;
        this.nodeParser = nodeParser;
    }

    private ItemResponseManager getResponseManager() {
        return new ItemResponseManager(xmlMapperProvider.get().getResponseDeclarations(), nodeParser);
    }

    @Override
    public ItemResponseManager get() {
        return getResponseManager();
    }
}
