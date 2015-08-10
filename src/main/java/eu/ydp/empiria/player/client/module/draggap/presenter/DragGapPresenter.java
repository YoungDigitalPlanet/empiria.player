package eu.ydp.empiria.player.client.module.draggap.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBaseBean;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public interface DragGapPresenter<T extends DragGapBaseBean> extends ActivityPresenter<DragGapModuleModel, T> {

    void setContent(String itemId);

    void removeContent();

    void setGapDimensions(HasDimensions size);
}
