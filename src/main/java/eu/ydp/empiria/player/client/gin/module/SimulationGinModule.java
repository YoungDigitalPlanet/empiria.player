package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.simulation.SimulationModuleView;
import eu.ydp.empiria.player.client.module.simulation.SimulationModuleViewImpl;

public class SimulationGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(SimulationModuleView.class).to(SimulationModuleViewImpl.class);
    }

}
