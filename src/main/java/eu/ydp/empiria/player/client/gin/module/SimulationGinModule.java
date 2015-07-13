package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.simulation.SimulationCanvasProvider;
import eu.ydp.empiria.player.client.module.simulation.SimulationController;
import eu.ydp.empiria.player.client.module.simulation.SimulationModuleView;
import eu.ydp.empiria.player.client.module.simulation.SimulationModuleViewImpl;
import eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsNative;
import eu.ydp.empiria.player.client.module.simulation.soundjs.SoundJsPlugin;

public class SimulationGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(SimulationModuleView.class).to(SimulationModuleViewImpl.class);
        bind(SimulationCanvasProvider.class).in(Singleton.class);
        bind(SimulationController.class).in(Singleton.class);
        bind(SoundJsNative.class).in(Singleton.class);
        bind(SoundJsPlugin.class).in(Singleton.class);
    }

}
