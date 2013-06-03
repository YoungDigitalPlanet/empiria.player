package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;


import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillCanvas;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillCanvasStubImpl;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenterImpl;import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillPalette;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillPaletteImpl;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillViewImpl;
import eu.ydp.empiria.player.client.module.colorfill.view.PaletteButton;
import eu.ydp.empiria.player.client.module.colorfill.view.PaletteButtonImpl;

public class ColorfillGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ColorfillInteractionPresenter.class).to(ColorfillInteractionPresenterImpl.class);
		bind(ColorfillInteractionView.class).to(ColorfillViewImpl.class);
		bind(ColorfillPalette.class).to(ColorfillPaletteImpl.class);
		bind(ColorfillCanvas.class).to(ColorfillCanvasStubImpl.class);
		bind(PaletteButton.class).to(PaletteButtonImpl.class);
	}

}
