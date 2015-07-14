package eu.ydp.empiria.player.client.module.colorfill.presenter.handlers;

import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillButtonClickListener;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public class ColorButtonClickListener implements ColorfillButtonClickListener {

    private final ColorfillInteractionPresenter interactionPresenter;

    public ColorButtonClickListener(ColorfillInteractionPresenter interactionPresenter) {
        this.interactionPresenter = interactionPresenter;
    }

    @Override
    public void onButtonClick(ColorModel color) {
        interactionPresenter.buttonClicked(color);
    }

}
