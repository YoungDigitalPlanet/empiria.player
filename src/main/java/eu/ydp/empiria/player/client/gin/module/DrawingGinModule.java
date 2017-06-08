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

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.drawing.DrawingView;
import eu.ydp.empiria.player.client.module.drawing.DrawingViewImpl;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingModelProvider;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxButtonCreator;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxPresenter;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxView;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxViewImpl;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasView;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasViewImpl;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class DrawingGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(DrawingView.class).to(DrawingViewImpl.class);
        bind(ToolboxView.class).to(ToolboxViewImpl.class);
        bind(DrawingBean.class).toProvider(DrawingModelProvider.class);
        bindModuleScoped(DrawingBean.class, new TypeLiteral<ModuleScopedProvider<DrawingBean>>() {
        });
        bindModuleScoped(DrawingView.class, new TypeLiteral<ModuleScopedProvider<DrawingView>>() {
        });
        bindModuleScoped(CanvasView.class, new TypeLiteral<ModuleScopedProvider<CanvasViewImpl>>() {
        });
        bindModuleScoped(DrawCanvas.class, new TypeLiteral<ModuleScopedProvider<CanvasViewImpl>>() {
        });
        bindModuleScoped(CanvasViewImpl.class, new TypeLiteral<ModuleScopedProvider<CanvasViewImpl>>() {
        });
        bindModuleScoped(ToolboxPresenter.class, new TypeLiteral<ModuleScopedProvider<ToolboxPresenter>>() {
        });
        bindModuleScoped(ToolboxView.class, new TypeLiteral<ModuleScopedProvider<ToolboxView>>() {
        });
        bindModuleScoped(ToolboxButtonCreator.class, new TypeLiteral<ModuleScopedProvider<ToolboxButtonCreator>>() {
        });
        bindModuleScoped(CanvasPresenter.class, new TypeLiteral<ModuleScopedProvider<CanvasPresenter>>() {
        });
    }

}
