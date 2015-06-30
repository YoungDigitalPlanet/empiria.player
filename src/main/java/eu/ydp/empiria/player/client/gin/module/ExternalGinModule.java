package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.factory.ExternalInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.view.ExternalInteractionViewImpl;
import eu.ydp.empiria.player.client.module.external.presentation.view.ExternalPresentationViewImpl;

public class ExternalGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<ExternalView<ExternalInteractionApi, ExternalInteractionEmpiriaApi>>() {
        }).to(ExternalInteractionViewImpl.class);
        bind(new TypeLiteral<ExternalView<ExternalApi, ExternalEmpiriaApi>>() {
        }).to(ExternalPresentationViewImpl.class);

        bind(ExternalStateEncoder.class).in(Singleton.class);
        install(new GinFactoryModuleBuilder().build(ExternalInteractionModuleFactory.class));
    }
}
