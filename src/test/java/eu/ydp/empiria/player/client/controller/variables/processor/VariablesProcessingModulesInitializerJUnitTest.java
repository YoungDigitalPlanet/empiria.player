package eu.ydp.empiria.player.client.controller.variables.processor;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;

@RunWith(MockitoJUnitRunner.class)
public class VariablesProcessingModulesInitializerJUnitTest {

	private VariablesProcessingModulesInitializer variablesProcessingModulesInitializer;

	@Mock
	private GroupedAnswersManager groupedAnswersManager;
	@Mock
	private ModulesVariablesProcessor modulesVariablesProcessor;
	@Mock
	private MistakesInitializer mistakesInitializer;

	@Before
	public void setUp() throws Exception {
		variablesProcessingModulesInitializer = new VariablesProcessingModulesInitializer(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
	}

	@After
	public void after() {
		verifyNoMoreInteractions(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
	}

	@Test
	public void shouldCallAllRelatedInitializations() throws Exception {
		Map<String, Response> responses = Maps.newHashMap();
		Map<String, Outcome> outcomes = Maps.newHashMap();
		variablesProcessingModulesInitializer.initializeVariableProcessingModules(responses, outcomes);

		InOrder inOrder = inOrder(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
		inOrder.verify(groupedAnswersManager).initialize(responses);
		inOrder.verify(modulesVariablesProcessor).initialize(responses);
		inOrder.verify(mistakesInitializer).initialize(outcomes);
	}
}
