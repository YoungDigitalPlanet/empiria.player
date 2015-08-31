package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.data.ItemDataSourceCollectionManager;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;

@Singleton
public class CurrentItemStyleProvider {

    private final ItemDataSourceCollectionManager itemDataSourceCollectionManager;
    private final ItemStylesContainer itemStylesContainer;
    private final FlowDataSupplier flowData;

    @Inject
    public CurrentItemStyleProvider(FlowDataSupplier flowData, ItemDataSourceCollectionManager itemDataSourceCollectionManager, ItemStylesContainer itemStylesContainer) {
        this.flowData = flowData;
        this.itemDataSourceCollectionManager = itemDataSourceCollectionManager;
        this.itemStylesContainer = itemStylesContainer;
    }

    public Optional<String> getCurrentItemStyle() {
        String itemIdentifier = getCurrentItemIdentifier();
        return itemStylesContainer.getStyle(itemIdentifier);
    }

    private String getCurrentItemIdentifier() {
        int pageIndex = flowData.getCurrentPageIndex();
        return itemDataSourceCollectionManager.getItemIdentifier(pageIndex);
    }
}
