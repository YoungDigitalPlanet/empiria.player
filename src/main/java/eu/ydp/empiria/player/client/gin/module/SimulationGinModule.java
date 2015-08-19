package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.gin.factory.ProgressBarFactory;
import eu.ydp.empiria.player.client.module.simulation.SimulationModuleView;
import eu.ydp.empiria.player.client.module.simulation.SimulationModuleViewImpl;
import eu.ydp.empiria.player.client.preloader.view.InfinityProgressWidget;
import eu.ydp.empiria.player.client.preloader.view.ProgressView;

public class SimulationGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(SimulationModuleView.class).to(SimulationModuleViewImpl.class);
        bind(ProgressView.class).to(InfinityProgressWidget.class);
        install(new GinFactoryModuleBuilder().build(ProgressBarFactory.class));
    }

}
