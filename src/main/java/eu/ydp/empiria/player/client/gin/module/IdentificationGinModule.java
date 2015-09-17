package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.gin.factory.IdentificationModuleFactory;
import eu.ydp.empiria.player.client.module.identification.math.view.IdentificationMathView;
import eu.ydp.empiria.player.client.module.identification.math.view.IdentificationMathViewImpl;
import eu.ydp.empiria.player.client.module.identification.view.SelectableChoiceView;
import eu.ydp.empiria.player.client.module.identification.view.SelectableChoiceViewImpl;

public class IdentificationGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(IdentificationMathView.class).to(IdentificationMathViewImpl.class);

        install(new GinFactoryModuleBuilder()
                .implement(SelectableChoiceView.class, SelectableChoiceViewImpl.class)
                .build(IdentificationModuleFactory.class));
    }
}
