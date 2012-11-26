package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.SimpleModuleCreator;

@SuppressWarnings("PMD")
public class SimpleModuleCreatorTest {

	Provider<IModule> provider;
	Factory<IModule> factory;
	IModule module;

	SimpleModuleCreator<IModule> instance;
	@Before
	public void before(){
		provider = mock(Provider.class);
		module = mock(IModule.class);
		when(provider.get()).thenReturn(module);
		factory = mock(Factory.class);
		when(factory.getNewInstance()).thenReturn(module);
	}

	@Test
	public void constructorTests(){
		instance = new SimpleModuleCreator<IModule>(factory, true, true);
		assertTrue(instance.isInlineModule());
		assertTrue(instance.isMultiViewModule());
		assertEquals(module, instance.createModule());

		instance = new SimpleModuleCreator<IModule>(factory, false, false);
		assertFalse(instance.isInlineModule());
		assertFalse(instance.isMultiViewModule());
		assertEquals(module, instance.createModule());

		instance = new SimpleModuleCreator<IModule>(provider, true, true);
		assertTrue(instance.isInlineModule());
		assertTrue(instance.isMultiViewModule());
		assertEquals(module, instance.createModule());

		instance = new SimpleModuleCreator<IModule>(provider, false, false);
		assertFalse(instance.isInlineModule());
		assertFalse(instance.isMultiViewModule());
		assertEquals(module, instance.createModule());

	}

}
