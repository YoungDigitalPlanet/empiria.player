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
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillModelProxy;
import eu.ydp.empiria.player.client.module.colorfill.presenter.*;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillBeanProxy;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionStructure;
import eu.ydp.empiria.player.client.module.colorfill.view.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class ColorfillGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(ColorfillInteractionPresenter.class).to(ColorfillInteractionPresenterImpl.class);
        bind(ColorfillInteractionView.class).to(ColorfillViewImpl.class);
        bind(ColorfillPalette.class).to(ColorfillPaletteImpl.class);
        bind(ColorfillCanvas.class).to(ColorfillCanvasImpl.class);
        bind(PaletteButton.class).to(PaletteButtonImpl.class);

        bindModuleScoped(ColorfillInteractionView.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionView>>() {
        });
        bindModuleScoped(ColorfillInteractionModuleModel.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionModuleModel>>() {
        });
        bindModuleScoped(ColorfillInteractionPresenter.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionPresenter>>() {
        });
        bindModuleScoped(ColorfillModelProxy.class, new TypeLiteral<ModuleScopedProvider<ColorfillModelProxy>>() {
        });
        bindModuleScoped(ColorfillInteractionViewColors.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionViewColors>>() {
        });
        bindModuleScoped(UserToResponseAreaMapper.class, new TypeLiteral<ModuleScopedProvider<UserToResponseAreaMapper>>() {
        });
        bindModuleScoped(ColorfillBeanProxy.class, new TypeLiteral<ModuleScopedProvider<ColorfillBeanProxy>>() {
        });
        bindModuleScoped(ColorfillInteractionStructure.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionStructure>>() {
        });
        bindModuleScoped(ResponseAnswerByViewBuilder.class, new TypeLiteral<ModuleScopedProvider<ResponseAnswerByViewBuilder>>() {
        });
    }

}
