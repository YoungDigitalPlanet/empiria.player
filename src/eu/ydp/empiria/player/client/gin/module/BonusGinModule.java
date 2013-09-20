package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupView;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupViewImpl;

public class BonusGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(BonusPopupPresenter.class).in(Singleton.class);
		bind(BonusPopupView.class).in(Singleton.class);
		bind(BonusPopupView.class).to(BonusPopupViewImpl.class);
	}

}
