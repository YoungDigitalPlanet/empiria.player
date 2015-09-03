package eu.ydp.empiria.player.client.module.dragdrop;

import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListLocking;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import java.util.List;

public interface Sourcelist extends IUniqueModule, SourceListLocking {

    SourcelistItemValue getItemValue(String itemId);

    void useItem(String itemId);

    void restockItem(String itemId);

    void useAndRestockItems(List<String> itemsIds);

    HasDimensions getItemSize();
}
