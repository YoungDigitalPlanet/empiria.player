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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.processor.module.expression.ExpressionModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.multiple.MultipleModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ordering.OrderingModeVariableProcessor;

public class VariableProcessorFactory {

    private final MultipleModeVariableProcessor multipleModeVariableProcessor;
    private final GroupedModeVariableProcessor groupedModeVariableProcessor;
    private final ExpressionModeVariableProcessor expressionModeVariableProcessor;
    private final OrderingModeVariableProcessor orderingModeVariableProcessor;

    @Inject
    public VariableProcessorFactory(MultipleModeVariableProcessor multipleModeVariableProcessor, GroupedModeVariableProcessor groupedModeVariableProcessor,
                                    ExpressionModeVariableProcessor expressionModeVariableProcessor, OrderingModeVariableProcessor orderingModeVariableProcessor) {
        this.multipleModeVariableProcessor = multipleModeVariableProcessor;
        this.groupedModeVariableProcessor = groupedModeVariableProcessor;
        this.expressionModeVariableProcessor = expressionModeVariableProcessor;
        this.orderingModeVariableProcessor = orderingModeVariableProcessor;
    }

    public VariableProcessor findAppropriateProcessor(Cardinality cardinality, boolean hasGroups, boolean isInExpression) {

        if (hasGroups) {
            return groupedModeVariableProcessor;
        }

        if (isInExpression) {
            return expressionModeVariableProcessor;
        }

        if (Cardinality.ORDERED == cardinality) {
            return orderingModeVariableProcessor;
        }

        return multipleModeVariableProcessor;

    }
}
