package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
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
