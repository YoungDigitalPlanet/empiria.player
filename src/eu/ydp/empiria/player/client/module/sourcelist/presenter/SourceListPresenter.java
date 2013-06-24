package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.dragdrop.Sourcelist;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public interface SourceListPresenter extends IsWidget, Sourcelist {
	void setBean(SourceListBean bean);
	void createAndBindUi();
	void onDragEvent(DragDropEventTypes eventType, String itemId);
	void setModule(IModule module);
	void setModuleId(String moduleId);
	DragDataObject getDragDataObject(String itemId);
}
