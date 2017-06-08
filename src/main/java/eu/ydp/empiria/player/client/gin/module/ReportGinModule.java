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
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.menu.view.MenuView;
import eu.ydp.empiria.player.client.module.menu.view.MenuViewImpl;
import eu.ydp.empiria.player.client.module.report.view.ReportView;
import eu.ydp.empiria.player.client.module.report.view.ReportViewImpl;

public class ReportGinModule extends AbstractGinModule{

    @Override
    protected void configure() {
        bind(ReportView.class).to(ReportViewImpl.class);
        bind(MenuView.class).to(MenuViewImpl.class);
        install(new GinFactoryModuleBuilder().build(ReportModuleFactory.class));
    }
}
