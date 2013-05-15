package eu.ydp.empiria.player.client.module.expression.evaluate;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.expression.ExpressionMode;

public class EvaluatorFactory {

	@Inject
	private Provider<ExpressionEvaluator> expressionEvaluatorProvider;
	
	public Evaluator createEvaluator(ExpressionMode expressionMode){
		return expressionEvaluatorProvider.get();
	}
}
