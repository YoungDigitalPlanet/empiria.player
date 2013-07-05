package eu.ydp.empiria.player.client.module.draggap.presenter;

import com.google.gwt.event.dom.client.DragEndHandler;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapDropHandler;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapStartDragHandler;

public interface DragGapPresenter extends ActivityPresenter<DragGapModuleModel, DragGapBean> {
	void setContent(String itemContent);
	void removeContent();
	void lockDropZone();
	void unlockDropZone();
	void setDropHandler(DragGapDropHandler dragGapDropHandler);
	void setDragStartHandler(DragGapStartDragHandler dragGapStartDragHandler);
	void setDragEndHandler(DragEndHandler dragEndHandler);
}
