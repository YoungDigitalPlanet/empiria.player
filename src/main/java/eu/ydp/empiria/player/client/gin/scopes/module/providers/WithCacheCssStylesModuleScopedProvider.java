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

package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.data.ElementStyleSelectorBuilder;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;
import java.util.Map;

@Singleton
public class WithCacheCssStylesModuleScopedProvider implements Provider<ModuleStyle> {

    @Inject
    private ElementStyleSelectorBuilder elementStyleSelectorBuilder;
    @Inject
    @ModuleScoped
    private Provider<Element> xmlProvider;
    @Inject
    @ModuleScoped
    private Provider<ModuleStyle> moduleStyleProvider;

    private final Map<String, ModuleStyle> cache = Maps.newHashMap();

    @Override
    public ModuleStyle get() {
        Element currentElement = xmlProvider.get();
        String cacheKey = getCacheKey(currentElement);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }
        return createModuleStyleAndPutToCache(cacheKey);
    }

    private ModuleStyle createModuleStyleAndPutToCache(String cacheKey) {
        ModuleStyle moduleStyle = createModuleStyle();
        cache.put(cacheKey, moduleStyle);
        return moduleStyle;
    }

    private ModuleStyle createModuleStyle() {
        return moduleStyleProvider.get();
    }

    private String getCacheKey(Element currentElement) {
        List<String> elementSelectors = elementStyleSelectorBuilder.getElementSelectors(currentElement);
        return Joiner.on(" ").join(elementSelectors);
    }

}
