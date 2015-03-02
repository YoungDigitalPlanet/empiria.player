package eu.ydp.empiria.player.client.module.colorfill.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public interface ColorfillInteractionPresenter extends ActivityPresenter<ColorfillInteractionModuleModel, ColorfillInteractionBean> {

	void imageColorChanged(Area area);

	void buttonClicked(ColorModel color);

}
