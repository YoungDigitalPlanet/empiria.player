package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillViewImpl;

public class ColorfillGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		//bind(ColorfillInteractionPresenter.class).to(ColorfillInteractionPresenterImpl.class);
		bind(ColorfillInteractionView.class).to(ColorfillViewImpl.class);
	}

}
