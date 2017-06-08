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

package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.inject.*;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.style.StyleSocket;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class VariableProcessorFunctionalTestBase {

    private static final Logger LOGGER = Logger.getLogger(VariableProcessorFunctionalTestBase.class.getName());

    protected VariablesProcessingInitializingWrapper defaultVariableProcessor;
    protected AnswerEvaluationSupplier answerEvaluationProvider;
    protected ProcessingMode processingMode;

    protected Injector injector;

    @Before
    public void setUp() {
        this.injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ModulesVariablesProcessor.class).annotatedWith(PageScoped.class).to(ModulesVariablesProcessor.class).in(Singleton.class);
                bind(AnswerEvaluationSupplier.class).annotatedWith(PageScoped.class).to(AnswerEvaluationSupplier.class).in(Singleton.class);
                bind(GroupedAnswersManager.class).annotatedWith(PageScoped.class).to(GroupedAnswersManager.class).in(Singleton.class);
                bind(ModulesProcessingResults.class).annotatedWith(PageScoped.class).to(ModulesProcessingResults.class).in(Singleton.class);
                bind(StyleSocket.class).toInstance(mock(StyleSocket.class));
                install(new FactoryModuleBuilder().build(ProcessingResultsToOutcomeMapConverterFactory.class));
            }
        });

        defaultVariableProcessor = injector.getInstance(VariablesProcessingInitializingWrapper.class);

        answerEvaluationProvider = injector.getInstance(Key.get(AnswerEvaluationSupplier.class, PageScoped.class));

        processingMode = ProcessingMode.USER_INTERACT;
    }

    protected void resetLastChangeRelatedVariables(Map<String, Outcome> outcomes) {
        for (String outcomeId : outcomes.keySet()) {
            Outcome outcome = outcomes.get(outcomeId);
            if (outcomeId.contains(VariableName.LASTMISTAKEN.toString())) {
                outcome.values = Lists.newArrayList(LastMistaken.NONE.toString());
            } else if (outcomeId.contains(VariableName.LASTCHANGE.toString())) {
                outcome.values = Lists.newArrayList();
            }
        }
    }

    protected void assertOutcomesMapsAreEqual(Map<String, Outcome> expectedOutcomes, Map<String, Outcome> actualOutcomes) {
        assertEquals(expectedOutcomes.size(), actualOutcomes.size());

        for (String key : expectedOutcomes.keySet()) {
            Outcome expectedOutcome = expectedOutcomes.get(key);
            Outcome actualOutcome = actualOutcomes.get(key);
            assertOuctomesAreEquals(expectedOutcome, actualOutcome);
        }
    }

    protected Map<String, Outcome> copyOutcomesMap(Map<String, Outcome> outcomes) {
        Map<String, Outcome> copyOfMap = new HashMap<String, Outcome>();

        for (String key : outcomes.keySet()) {
            Outcome currentOutcome = outcomes.get(key);
            Outcome copyOfOutcome = copyOutcome(currentOutcome);
            copyOfMap.put(key, copyOfOutcome);
        }

        return copyOfMap;
    }

    protected void assertResponseRelatedOutcomesHaveValue(Response response, List<String> expectedValues, List<VariableName> responseIdentifiers,
                                                          Map<String, Outcome> outcomes) {
        assertOutcomesHaveValue(expectedValues, buildIdenfitiers(response.getID(), responseIdentifiers), outcomes);
    }

    protected void assertGlobalOutcomesHaveValue(List<String> expectedValues, List<VariableName> responseIdentifiers, Map<String, Outcome> outcomes) {

        assertOutcomesHaveValue(expectedValues, convertToString(responseIdentifiers), outcomes);
    }

    private List<String> convertToString(List<VariableName> responseIdentifiers) {
        List<String> converted = Lists.newArrayList();
        for (VariableName variablesNames : responseIdentifiers) {
            converted.add(variablesNames.toString());
        }
        return converted;
    }

    protected Map<String, Outcome> prepareInitialOutcomes(Map<String, Response> responsesMap) {
        Map<String, Outcome> outcomes = Maps.newHashMap();
        return outcomes;
    }

    protected Map<String, Response> convertToMap(Response... responses) {
        Map<String, Response> responsesMap = Maps.newHashMap();
        for (Response response : responses) {
            if (responsesMap.containsKey(response.getID())) {
                throw new IllegalArgumentException("Response with id: " + response.getID() + " already exists in map!");
            }
            responsesMap.put(response.getID(), response);
        }
        return responsesMap;
    }

    protected void setUpCurrentUserAnswers(Response response, String... currentAnswers) {
        response.values = Arrays.asList(currentAnswers);
    }

    private void assertOuctomesAreEquals(Outcome expectedOutcome, Outcome actualOutcome) {
        assertEquals(expectedOutcome.identifier, actualOutcome.identifier);
        assertEquals(expectedOutcome.values, actualOutcome.values);
    }

    private Outcome copyOutcome(Outcome currentOutcome) {
        Outcome copyOfOutcome = new Outcome();
        copyOfOutcome.values = new ArrayList<String>(currentOutcome.values);
        copyOfOutcome.identifier = currentOutcome.identifier;
        return copyOfOutcome;
    }

    private List<String> buildIdenfitiers(String prefix, Iterable<VariableName> variables) {
        List<String> identifiers = Lists.newArrayList();
        for (VariableName variable : variables) {
            String currentIdentifier = prefix + "-" + variable;
            identifiers.add(currentIdentifier);
        }
        return identifiers;
    }

    private void assertOutcomesHaveValue(List<String> expectedValues, List<String> responseIdentifiers, Map<String, Outcome> outcomes) {
        for (String responseIdentifier : responseIdentifiers) {
            Outcome outcome = outcomes.get(responseIdentifier);
            LOGGER.info("Asserting variable with identifier: " + responseIdentifier);
            assertOutcomeValue(outcome, expectedValues);
        }
    }

    private void assertOutcomeValue(Outcome outcome, List<String> expectedValues) {
        List<String> currentOutcomeValues = outcome.values;
        assertEquals(expectedValues, currentOutcomeValues);
    }

}
