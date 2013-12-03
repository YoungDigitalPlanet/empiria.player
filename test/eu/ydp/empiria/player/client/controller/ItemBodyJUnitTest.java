package eu.ydp.empiria.player.client.controller;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;

public class ItemBodyJUnitTest {

	private static ItemBody itemBody;

	@Before
	public void prepare() {
		itemBody = new ItemBody(null, null, null, null, null, null);

		/**
		 * Prepares base list of mock modules that contains only non-interaction
		 * one
		 */
		itemBody.modules = new ArrayList<IModule>();

		IModule testNonInteractionModule = mock(IModule.class);
		itemBody.modules.add(testNonInteractionModule);

		IModule testNonInteractionModule2 = mock(IModule.class);
		itemBody.modules.add(testNonInteractionModule2);
	}

	@Test
	@SuppressWarnings("PMD")
	public void hasInteractiveModulesNullGivenExpectsFalse() {
		itemBody.modules = null;

		assertThat(itemBody.hasInteractiveModules(), equalTo(false));
	}

	@Test
	public void hasInteractiveModulesBasicModulesGivenExpectsFalse() {
		assertThat(itemBody.hasInteractiveModules(), equalTo(false));
	}

	@Test
	public void hasInteractiveModulesInteractionModuleGivenExpectsTrue() {
		IInteractionModule testInteractionModule = mock(IInteractionModule.class);
		itemBody.modules.add(testInteractionModule);

		assertThat(itemBody.hasInteractiveModules(), equalTo(true));
	}
}
