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

package eu.ydp.empiria.player.client.controller.variables.processor.module;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.processor.module.expression.ExpressionModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.multiple.MultipleModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ordering.OrderingModeVariableProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessorFactoryJUnitTest {

    private VariableProcessorFactory variableProcessorFactory;

    @Mock
    private MultipleModeVariableProcessor multipleModeVariableProcessor;
    @Mock
    private GroupedModeVariableProcessor groupedModeVariableProcessor;
    @Mock
    private ExpressionModeVariableProcessor expressionVariableProcessor;
    @Mock
    private OrderingModeVariableProcessor orderingModeVariableProcessor;

    @Before
    public void setUp() throws Exception {
        variableProcessorFactory = new VariableProcessorFactory(multipleModeVariableProcessor, groupedModeVariableProcessor, expressionVariableProcessor,
                orderingModeVariableProcessor);
    }

    @Test
    public void shouldCreateGroupedProcessorWhenHasGroup() throws Exception {
        Cardinality cardinality = Cardinality.ORDERED;
        boolean hasGroups = true;
        boolean isInExpression = false;

        VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

        assertEquals(groupedModeVariableProcessor, variableProcessor);
    }

    @Test
    public void shouldCreateMultipleModeProcessorIsSingleCardinality() throws Exception {
        Cardinality cardinality = Cardinality.SINGLE;
        boolean hasGroups = false;
        boolean isInExpression = false;

        VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

        assertEquals(multipleModeVariableProcessor, variableProcessor);
    }

    @Test
    public void shouldCreateMultipleModeProcessorIsMultipleCardinality() throws Exception {
        Cardinality cardinality = Cardinality.MULTIPLE;
        boolean hasGroups = false;
        boolean isInExpression = false;

        VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

        assertEquals(multipleModeVariableProcessor, variableProcessor);
    }

    @Test
    public void shouldCreateMultipleModeProcessorWhenIsNotSupportedCardinality() throws Exception {
        Cardinality cardinality = Cardinality.ORDERED;
        boolean hasGroups = false;
        boolean isInExpression = false;

        VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

        assertEquals(orderingModeVariableProcessor, variableProcessor);
    }

    @Test
    public void shouldCreateExpressionVariableProcessor() throws Exception {
        Cardinality cardinality = Cardinality.SINGLE;
        boolean hasGroups = false;
        boolean isInExpression = true;

        VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

        assertEquals(expressionVariableProcessor, variableProcessor);
    }
}
