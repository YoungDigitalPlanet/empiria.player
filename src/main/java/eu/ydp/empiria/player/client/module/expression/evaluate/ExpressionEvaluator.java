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

package eu.ydp.empiria.player.client.module.expression.evaluate;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.expression.ExpressionCreator;
import eu.ydp.empiria.player.client.module.expression.adapters.SymjaExpressionCharactersAdapter;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.gwtutil.client.debug.gwtlogger.ILogger;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;
import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.eval.DoubleEvaluator;

public class ExpressionEvaluator implements Evaluator {

    @Inject
    private ExpressionCreator expressionCreator;

    @Inject
    private SymjaExpressionCharactersAdapter symjaExpressionAdapter;

    ILogger log = new Logger();

    @Override
    public boolean evaluate(ExpressionBean expressionBean) {
        String expression = prepareExpression(expressionBean);
        String fixedExpression = symjaExpressionAdapter.process(expression);
        boolean result = processEvaluation(fixedExpression);
        return result;
    }

    private boolean processEvaluation(String expression) {
        boolean result;
        try {
            result = processLogicalExpressionEvaluator(expression);
        } catch (ArithmeticException e) {
            result = false;
        } catch (SyntaxError e) {
            result = false;
        } catch (Exception e) {
            result = false;
            log.severe(getSeverErrorMessage(expression, e));
        }
        return result;
    }

    private boolean processLogicalExpressionEvaluator(String expression) {
        Parser p = new Parser();
        ASTNode obj = p.parse(expression);
        DoubleEvaluator engine = new DoubleEvaluator();
        return engine.evaluateNodeLogical(obj);
    }

    private String prepareExpression(ExpressionBean expressionBean) {
        return expressionCreator.getExpression(expressionBean);
    }

    private String getSeverErrorMessage(String expression, Exception e) {
        return "Expression evaluation problem for: " + expression + ":: \n\n " + e.toString() + " " + e.toString();
    }
}
