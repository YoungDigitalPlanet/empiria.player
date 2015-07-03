package eu.ydp.empiria.player.client.module.colorfill.presenter.handlers;

import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillAreaClickListener;

public class AreaClickListener implements ColorfillAreaClickListener {

    private final ColorfillInteractionPresenter interactionPresenter;

    public AreaClickListener(ColorfillInteractionPresenter interactionPresenter) {
        this.interactionPresenter = interactionPresenter;
    }

    @Override
    public void onAreaClick(Area area) {
        interactionPresenter.imageColorChanged(area);
    }

}
