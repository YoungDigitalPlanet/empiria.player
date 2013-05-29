package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionViewImpl;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionViewWidget;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionViewWidgetImpl;

public class ColorfillGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		//bind(ColorfillInteractionPresenter.class).to(ColorfillInteractionPresenterImpl.class);
		bind(ColorfillInteractionView.class).to(ColorfillInteractionViewImpl.class);
		bind(ColorfillInteractionViewWidget.class).to(ColorfillInteractionViewWidgetImpl.class);
	}

}
