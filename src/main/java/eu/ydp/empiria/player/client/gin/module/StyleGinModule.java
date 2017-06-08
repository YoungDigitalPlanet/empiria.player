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

import eu.ydp.empiria.player.client.controller.data.StyleDataSourceManager;
import eu.ydp.empiria.player.client.gin.binding.CachedModuleScoped;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.CssStylesModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.WithCacheCssStylesModuleScopedProvider;
import eu.ydp.empiria.player.client.style.ComputedStyle;
import eu.ydp.empiria.player.client.style.ComputedStyleImpl;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelper;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelperImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class StyleGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(ModuleStyle.class).annotatedWith(ModuleScoped.class).toProvider(CssStylesModuleScopedProvider.class);
        bind(ModuleStyle.class).annotatedWith(CachedModuleScoped.class).toProvider(WithCacheCssStylesModuleScopedProvider.class);
        bind(StyleSocket.class).to(StyleDataSourceManager.class);
        bind(ComputedStyle.class).to(ComputedStyleImpl.class);
        bind(NativeStyleHelper.class).to(NativeStyleHelperImpl.class);
    }
}
