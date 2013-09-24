package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyRuntimeLoader;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusService;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupView;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupViewImpl;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackTutorClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackTutorClient;

public class BonusGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(BonusPopupPresenter.class).in(Singleton.class);
		bind(SwiffyRuntimeLoader.class).in(Singleton.class);
		bind(BonusService.class).in(Singleton.class);
		bind(BonusPopupView.class).in(Singleton.class);
		bind(BonusPopupView.class).to(BonusPopupViewImpl.class);
		bind(PowerFeedbackBonusClient.class).to(NullPowerFeedbackBonusClient.class);
		bind(PowerFeedbackTutorClient.class).to(NullPowerFeedbackTutorClient.class);
	}

}
