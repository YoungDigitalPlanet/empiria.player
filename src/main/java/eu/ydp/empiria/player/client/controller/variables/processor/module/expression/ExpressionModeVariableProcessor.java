package eu.ydp.empiria.player.client.controller.variables.processor.module.expression;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.module.expression.ExpressionEvaluationController;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult;

import java.util.List;

public class ExpressionModeVariableProcessor implements VariableProcessor {

    private final ExpressionEvaluationController expressionEvaluationController;

    @Inject
    public ExpressionModeVariableProcessor(ExpressionEvaluationController expressionEvaluationController) {
        this.expressionEvaluationController = expressionEvaluationController;
    }

    @Override
    public int calculateErrors(Response response) {
        ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);

        if (evaluationResult == ExpressionEvaluationResult.WRONG) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int calculateDone(Response response) {
        ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);

        if (evaluationResult == ExpressionEvaluationResult.CORRECT) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public LastMistaken checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
        LastMistaken lastMistaken = LastMistaken.NONE;
        if (answersChanges.containChanges()) {
            ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);
            if (evaluationResult == ExpressionEvaluationResult.WRONG) {
                lastMistaken = LastMistaken.WRONG;
            } else if (evaluationResult == ExpressionEvaluationResult.CORRECT) {
                lastMistaken = LastMistaken.CORRECT;
            }
        }

        return lastMistaken;
    }

    @Override
    public int calculateMistakes(LastMistaken lastMistaken, int previousMistakes) {
        if (lastMistaken == LastMistaken.WRONG) {
            return previousMistakes + 1;
        } else {
            return previousMistakes;
        }
    }

    @Override
    public List<Boolean> evaluateAnswers(Response response) {
        ExpressionEvaluationResult evaluationResult = calculateExpressionOfResponse(response);

        List<Boolean> evaluation = Lists.newArrayList();
        if (evaluationResult == ExpressionEvaluationResult.CORRECT) {
            evaluation.add(true);
        } else {
            evaluation.add(false);
        }
        return evaluation;
    }

    private ExpressionEvaluationResult calculateExpressionOfResponse(Response response) {
        ExpressionBean expression = response.getExpression();
        ExpressionEvaluationResult evaluationResult = expressionEvaluationController.evaluateExpression(expression);
        return evaluationResult;
    }

}
