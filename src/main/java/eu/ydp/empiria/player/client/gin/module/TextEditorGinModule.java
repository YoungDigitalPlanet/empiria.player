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
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBean;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBeanProvider;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorViewImpl;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorOptions;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorOptionsProvider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class TextEditorGinModule extends BaseGinModule {
    @Override
    protected void configure() {
        bind(TextEditorView.class).to(TextEditorViewImpl.class);
        bind(TextEditorOptions.class).toProvider(TextEditorOptionsProvider.class);
        bind(TextEditorBean.class).toProvider(TextEditorBeanProvider.class);

        bindModuleScoped(TextEditorBean.class, new TypeLiteral<ModuleScopedProvider<TextEditorBean>>() {
        });
        bindModuleScoped(TextEditorPresenter.class, new TypeLiteral<ModuleScopedProvider<TextEditorPresenter>>() {
        });
        bindModuleScoped(TextEditorView.class, new TypeLiteral<ModuleScopedProvider<TextEditorView>>() {
        });
    }
}
