package eu.ydp.empiria.player.client.controller.item;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;

public class ItemDataProvider implements Provider<ItemData> {

    @Inject
    private PageScopeFactory pageScopeFactory;

    @Inject
    private DataSourceManager dataSourceManager;

    @Override
    public ItemData get() {
        return dataSourceManager.getItemData(pageScopeFactory.getCurrentPageScope().getPageIndex());
    }

}
