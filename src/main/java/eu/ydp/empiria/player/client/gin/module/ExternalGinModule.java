package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.factory.ExternalInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.common.sound.ExternalSoundInstanceCreator;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;
import eu.ydp.empiria.player.client.module.external.interaction.ExternalInteractionResponseModel;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalApiProvider;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.structure.ExternalInteractionModuleStructure;
import eu.ydp.empiria.player.client.module.external.interaction.view.ExternalInteractionViewImpl;
import eu.ydp.empiria.player.client.module.external.presentation.view.ExternalPresentationViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class ExternalGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<ExternalView<ExternalInteractionApi, ExternalInteractionEmpiriaApi>>() {
        }).to(ExternalInteractionViewImpl.class);
        bind(new TypeLiteral<ExternalView<ExternalApi, ExternalEmpiriaApi>>() {
        }).to(ExternalPresentationViewImpl.class);

        bindModuleScoped(ExternalInteractionResponseModel.class, new TypeLiteral<ModuleScopedProvider<ExternalInteractionResponseModel>>() {
        });
        bindModuleScoped(ExternalInteractionModuleStructure.class, new TypeLiteral<ModuleScopedProvider<ExternalInteractionModuleStructure>>() {
        });
        bindModuleScoped(ExternalPaths.class, new TypeLiteral<ModuleScopedProvider<ExternalPaths>>() {
        });
        bindModuleScoped(ExternalSoundInstanceCreator.class, new TypeLiteral<ModuleScopedProvider<ExternalSoundInstanceCreator>>() {
        });
        bindModuleScoped(ExternalInteractionEmpiriaApi.class, new TypeLiteral<ModuleScopedProvider<ExternalInteractionEmpiriaApi>>() {
        });
        bindModuleScoped(ExternalStateSaver.class, new TypeLiteral<ModuleScopedProvider<ExternalStateSaver>>() {
        });
        bindModuleScoped(ExternalApiProvider.class, new TypeLiteral<ModuleScopedProvider<ExternalApiProvider>>() {
        });

        install(new GinFactoryModuleBuilder().build(ExternalInteractionModuleFactory.class));
    }
}
