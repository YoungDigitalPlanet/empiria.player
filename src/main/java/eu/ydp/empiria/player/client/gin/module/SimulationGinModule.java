/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
