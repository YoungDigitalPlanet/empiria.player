package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListLocking;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.internal.dragdrop.DragDropEventTypes;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import java.util.List;

public interface SourceListPresenter extends IsWidget, SourceListLocking {
    void setBean(SourceListBean bean);

    void createAndBindUi();

    void onDragEvent(DragDropEventTypes eventType, String itemId);

    void onDropEvent(String itemId, String sourceModuleId);

    void setModuleId(String moduleId);

    void useItem(String itemId);

    void restockItem(String itemId);

    void useAndRestockItems(List<String> itemsIds);

    DragDataObject getDragDataObject(String itemId);

    SourcelistItemValue getItemValue(String itemId);

    HasDimensions getMaxItemSize();

}
