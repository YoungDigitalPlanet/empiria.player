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

package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.core.creator.SimpleModuleCreator;

/**
 * Klasa bedaca podstawowowa implementacja ModuleConnectorExtension.<br/>
 */
public class SimpleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension {

    private final Provider<? extends IModule> clazzProvider;
    private final String tagName;
    private boolean isMultiViewModule = false;
    private boolean isInlineModule = false;

    /**
     * domyslnie isMultiViewModule=false,isInlineModule=false
     *
     * @param clazz   klasa modulu
     * @param tagName nazwa taga kt�ry ma by obslugiwany
     */
    public SimpleConnectorExtension(Provider<? extends IModule> clazz, ModuleTagName tagName) {
        this(clazz, tagName, false);
    }

    public SimpleConnectorExtension(Provider<? extends IModule> clazz, ModuleTagName tagName, boolean isMultiViewModule) {
        this(clazz, tagName, isMultiViewModule, false);
    }

    public SimpleConnectorExtension(Provider<? extends IModule> clazzProvider, ModuleTagName tagName, boolean isMultiViewModule, boolean isInlineModule) {
        this.tagName = tagName.tagName();
        this.isMultiViewModule = isMultiViewModule;
        this.isInlineModule = isInlineModule;
        this.clazzProvider = clazzProvider;
    }

    @Override
    public ModuleCreator getModuleCreator() {
        return new SimpleModuleCreator<>(clazzProvider, isMultiViewModule, isInlineModule);
    }

    @Override
    public String getModuleNodeName() {
        return tagName;
    }

}
