package eu.ydp.empiria.player.client.module.expression.evaluate;

import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public interface Evaluator {

    boolean evaluate(ExpressionBean bean);
}
