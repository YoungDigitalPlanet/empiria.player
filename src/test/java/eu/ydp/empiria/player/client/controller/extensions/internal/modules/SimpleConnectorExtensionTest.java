package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;

@SuppressWarnings("PMD")
public class SimpleConnectorExtensionTest {

	Factory<IModule> factory;
	Provider<IModule> provider;

	@Before
	public void before() {
		factory = mock(Factory.class);
		provider = mock(Provider.class);
	}

	@Test
	public void constructorsWithFactoryTest() {
		ModuleCreator moduleCreator;
		SimpleConnectorExtension extension = new SimpleConnectorExtension(factory, ModuleTagName.AUDIO_PLAYER);
		moduleCreator = extension.getModuleCreator();
		assertEquals(ModuleTagName.AUDIO_PLAYER.tagName(), extension.getModuleNodeName());
		assertFalse(moduleCreator.isInlineModule());
		assertFalse(moduleCreator.isMultiViewModule());

		extension = new SimpleConnectorExtension(factory, ModuleTagName.AUDIO_PLAYER, true);
		moduleCreator = extension.getModuleCreator();
		assertEquals(ModuleTagName.AUDIO_PLAYER.tagName(), extension.getModuleNodeName());
		assertFalse(moduleCreator.isInlineModule());
		assertTrue(moduleCreator.isMultiViewModule());

		extension = new SimpleConnectorExtension(factory, ModuleTagName.AUDIO_PLAYER, true, true);
		moduleCreator = extension.getModuleCreator();
		assertEquals(ModuleTagName.AUDIO_PLAYER.tagName(), extension.getModuleNodeName());
		assertTrue(moduleCreator.isInlineModule());
		assertTrue(moduleCreator.isMultiViewModule());
	}

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
