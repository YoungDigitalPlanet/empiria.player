package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.BonusConfigModuleScopeProvider;
import eu.ydp.empiria.player.client.module.bonus.BonusProvider;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupView;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupViewImpl;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackTutorClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackTutorClient;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class BonusGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(BonusPopupView.class).to(BonusPopupViewImpl.class);
        bind(PowerFeedbackBonusClient.class).to(NullPowerFeedbackBonusClient.class);
        bind(PowerFeedbackTutorClient.class).to(NullPowerFeedbackTutorClient.class);
        bind(BonusConfig.class).annotatedWith(ModuleScoped.class).toProvider(BonusConfigModuleScopeProvider.class);
        bindModuleScoped(BonusProvider.class, new TypeLiteral<ModuleScopedProvider<BonusProvider>>() {
        });

    }

}
