package eu.ydp.empiria.player.client.module.draggap.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.view.HasDimensions;

public interface DragGapPresenter extends ActivityPresenter<DragGapModuleModel, DragGapBean> {

	void setContent(String itemId);

	void removeContent();

	void setGapDimensions(HasDimensions size);
}
