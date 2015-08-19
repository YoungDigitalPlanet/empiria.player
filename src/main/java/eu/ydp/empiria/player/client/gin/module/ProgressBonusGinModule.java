package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.ProgressBonusConfigModuleScopeProvider;
import eu.ydp.empiria.player.client.module.progressbonus.presenter.ProgressBonusPresenter;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class ProgressBonusGinModule extends EmpiriaModule {

    @Override
    protected void configure() {
        bind(ProgressBonusView.class).to(ProgressBonusViewImpl.class);
        bindModuleScoped(ProgressBonusPresenter.class, new TypeLiteral<ModuleScopedProvider<ProgressBonusPresenter>>() {
        });
        bindModuleScoped(ProgressBonusView.class, new TypeLiteral<ModuleScopedProvider<ProgressBonusView>>() {
        });
        bind(ProgressBonusConfig.class).annotatedWith(ModuleScoped.class).toProvider(ProgressBonusConfigModuleScopeProvider.class);

    }
}
