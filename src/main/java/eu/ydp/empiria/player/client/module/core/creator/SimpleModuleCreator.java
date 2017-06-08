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

package eu.ydp.empiria.player.client.module.core.creator;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.core.base.IModule;

/**
 * Prosta implementacja ModuleCreator<br/>
 */
public class SimpleModuleCreator<T extends IModule> implements ModuleCreator {

    private final boolean inlineModule;
    private final boolean multiViewModule;
    private final Provider<T> provider;

    protected SimpleModuleCreator(boolean isMultiViewModule, boolean isInlineModule) {
        this.inlineModule = isInlineModule;
        this.multiViewModule = isMultiViewModule;
        this.provider = null;
    }

    public SimpleModuleCreator(Provider<T> provider, boolean isMultiViewModule, boolean isInlineModule) {
        this.inlineModule = isInlineModule;
        this.multiViewModule = isMultiViewModule;
        this.provider = provider;
    }

    @Override
    public boolean isMultiViewModule() {
        return multiViewModule;
    }

    @Override
    public boolean isInlineModule() {
        return inlineModule;
    }

    @Override
    public T createModule() {
        return provider.get();
    }

}
