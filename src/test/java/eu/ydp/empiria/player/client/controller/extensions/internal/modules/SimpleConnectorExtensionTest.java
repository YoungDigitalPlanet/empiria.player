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
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SimpleConnectorExtensionTest {

    Provider<IModule> provider = mock(Provider.class);

    @Test
    public void constructorsWithProviderTest() {
        ModuleCreator moduleCreator;
        SimpleConnectorExtension extension = new SimpleConnectorExtension(provider, ModuleTagName.AUDIO_PLAYER);
        moduleCreator = extension.getModuleCreator();
        assertEquals(ModuleTagName.AUDIO_PLAYER.tagName(), extension.getModuleNodeName());
        assertFalse(moduleCreator.isInlineModule());
        assertFalse(moduleCreator.isMultiViewModule());

        extension = new SimpleConnectorExtension(provider, ModuleTagName.AUDIO_PLAYER, true);
        moduleCreator = extension.getModuleCreator();
        assertEquals(ModuleTagName.AUDIO_PLAYER.tagName(), extension.getModuleNodeName());
        assertFalse(moduleCreator.isInlineModule());
        assertTrue(moduleCreator.isMultiViewModule());

        extension = new SimpleConnectorExtension(provider, ModuleTagName.AUDIO_PLAYER, true, true);
        moduleCreator = extension.getModuleCreator();
        assertEquals(ModuleTagName.AUDIO_PLAYER.tagName(), extension.getModuleNodeName());
        assertTrue(moduleCreator.isInlineModule());
        assertTrue(moduleCreator.isMultiViewModule());
    }

}
