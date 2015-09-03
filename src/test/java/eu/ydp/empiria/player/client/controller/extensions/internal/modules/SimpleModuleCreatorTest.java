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
