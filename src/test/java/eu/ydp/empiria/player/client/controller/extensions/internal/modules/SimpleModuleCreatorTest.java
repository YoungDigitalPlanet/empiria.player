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
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.SimpleModuleCreator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleModuleCreatorTest {

    Provider<IModule> provider;
    IModule module;

    SimpleModuleCreator<IModule> instance;

    @Before
    public void before() {
        provider = mock(Provider.class);
        module = mock(IModule.class);
        when(provider.get()).thenReturn(module);
    }

    @Test
    public void constructorTests() {
        instance = new SimpleModuleCreator<>(provider, true, true);
        assertTrue(instance.isInlineModule());
        assertTrue(instance.isMultiViewModule());
        assertEquals(module, instance.createModule());

        instance = new SimpleModuleCreator<>(provider, false, false);
        assertFalse(instance.isInlineModule());
        assertFalse(instance.isMultiViewModule());
        assertEquals(module, instance.createModule());

    }

}
