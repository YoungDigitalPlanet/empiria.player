package eu.ydp.empiria.player.client.module.draggap.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.model.DragData;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;

public interface DragGapPresenter extends ActivityPresenter<DragGapModuleModel, DragGapBean> {
	DragData getDragData();
	void onDragDone(DragData data);
	void onDragCancelled();
}
