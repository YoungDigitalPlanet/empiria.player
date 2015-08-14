package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupView;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupViewImpl;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackTutorClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackTutorClient;

public class BonusGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(BonusPopupView.class).to(BonusPopupViewImpl.class);
        bind(PowerFeedbackBonusClient.class).to(NullPowerFeedbackBonusClient.class);
        bind(PowerFeedbackTutorClient.class).to(NullPowerFeedbackTutorClient.class);
    }

}
