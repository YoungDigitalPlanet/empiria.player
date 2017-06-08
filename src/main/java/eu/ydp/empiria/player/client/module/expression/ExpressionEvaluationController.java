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

package eu.ydp.empiria.player.client.module.expression;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.expression.evaluate.Evaluator;
import eu.ydp.empiria.player.client.module.expression.evaluate.EvaluatorFactory;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult;

import static eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult.*;

public class ExpressionEvaluationController {

    @Inject
    private ExpressionValidator expressionValidator;

    @Inject
    private EvaluatorFactory factory;

    public ExpressionEvaluationResult evaluateExpression(ExpressionBean expressionBean) {
        ExpressionEvaluationResult evaluationResult;
        boolean expressionValid = validateExpressionAgainstNotEmpty(expressionBean);
        if (expressionValid) {
            evaluationResult = evaluate(expressionBean);
        } else {
            evaluationResult = VALUES_NOT_SET;
        }
        return evaluationResult;
    }

    private boolean validateExpressionAgainstNotEmpty(ExpressionBean expressionBean) {
        return expressionValidator.isAllResponsesAreNotEmpty(expressionBean);
    }

    private ExpressionEvaluationResult evaluate(ExpressionBean expressionBean) {
        Evaluator evaluator = factory.createEvaluator(expressionBean.getMode());
        boolean expressionEvaluationResult = evaluator.evaluate(expressionBean);
        return (expressionEvaluationResult) ? CORRECT : WRONG;
    }
}
