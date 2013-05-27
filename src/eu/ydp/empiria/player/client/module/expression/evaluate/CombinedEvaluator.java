package eu.ydp.empiria.player.client.module.expression.evaluate;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;


public class CombinedEvaluator implements Evaluator {

	private ExpressionEvaluator expressionEvaluator;
	private CommutationEvaluator commutationEvaluator;

	@Inject
	public CombinedEvaluator(ExpressionEvaluator expressionEvaluator, CommutationEvaluator commutationEvaluator) {
		this.expressionEvaluator = expressionEvaluator;
		this.commutationEvaluator = commutationEvaluator;
	}

	@Override
	public boolean evaluate(ExpressionBean bean) {		
		return expressionEvaluator.evaluate(bean) &&  commutationEvaluator.evaluate(bean);
	}
	
}
