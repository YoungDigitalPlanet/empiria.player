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

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.DictionaryPresenter;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.*;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.external.view.visibility.VisibilityChanger;
import eu.ydp.empiria.player.client.module.dictionary.external.view.visibility.VisibilityChangerProvider;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;
import eu.ydp.jsfilerequest.client.FileRequestCallback;

public class DictionaryGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(DictionaryButtonView.class).to(DictionaryButtonViewImpl.class);
        bind(DictionaryPopupView.class).to(DictionaryPopupViewImpl.class);
        bind(WordsLoadingListener.class).to(MainController.class);
        bind(ExplanationListener.class).to(MainController.class);
        bind(WordsSocket.class).to(WordsController.class);
        bind(ExplanationView.class).to(ExplanationViewImpl.class);
        bind(VisibilityChanger.class).toProvider(VisibilityChangerProvider.class);

        bindModuleScoped(DictionaryPresenter.class, new TypeLiteral<ModuleScopedProvider<DictionaryPresenter>>() {
        });
        bindModuleScoped(DictionaryButtonView.class, new TypeLiteral<ModuleScopedProvider<DictionaryButtonView>>() {
        });
        bindModuleScoped(DictionaryPopupView.class, new TypeLiteral<ModuleScopedProvider<DictionaryPopupView>>() {
        });

        install(new GinFactoryModuleBuilder().implement(FileRequestCallback.class, DictionaryFileRequestCallback.class).build(DictionaryModuleFactory.class));

    }
}
