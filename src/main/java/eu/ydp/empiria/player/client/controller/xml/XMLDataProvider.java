package eu.ydp.empiria.player.client.controller.xml;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

@Singleton
public class XMLDataProvider implements Provider<XmlData> {

    @Inject
    private PageScopeFactory pageScopeFactory;

    @Inject
    private DataSourceManager dataSourceManager;

    @Override
    public XmlData get() {
        int pageIndex = pageScopeFactory.getCurrentPageScope().getPageIndex();
        return dataSourceManager.getItemData(pageIndex).getData();
    }

}
