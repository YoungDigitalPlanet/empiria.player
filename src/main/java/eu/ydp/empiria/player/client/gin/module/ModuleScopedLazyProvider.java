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

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

public class ModuleScopedLazyProvider<T> implements Provider<T> {

    private final Provider<T> instanceProvider;
    private final ModuleScopeStack moduleScopeStack;
    private final ModuleCreationContext currentTopContext;

    @Inject
    public ModuleScopedLazyProvider(Provider<T> instanceProvider, ModuleScopeStack moduleScopeStack) {
        this.instanceProvider = instanceProvider;
        this.moduleScopeStack = moduleScopeStack;

        currentTopContext = moduleScopeStack.getCurrentTopContext();
    }

    @Override
    public T get() {
        moduleScopeStack.pushContext(currentTopContext);
        T instance = instanceProvider.get();
        moduleScopeStack.pop();
        return instance;
    }
}
