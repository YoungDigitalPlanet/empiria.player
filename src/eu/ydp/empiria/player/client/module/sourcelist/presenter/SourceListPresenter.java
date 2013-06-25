package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public interface SourceListPresenter extends IsWidget {
	void setBean(SourceListBean bean);
	void createAndBindUi();
	void onDragEvent(DragDropEventTypes eventType, String itemId);
	void setModuleId(String moduleId);
	void useItem(String itemId);
	void restockItem(String itemId);
	void useAndRestockItems(List<String> itemsIds);
	DragDataObject getDragDataObject(String itemId);
	String getItemValue(String itemId);

}
