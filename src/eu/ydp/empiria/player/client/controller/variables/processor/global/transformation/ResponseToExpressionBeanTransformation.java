package eu.ydp.empiria.player.client.controller.variables.processor.global.transformation;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ResponseToExpressionBeanTransformation implements Function<Response, ExpressionBean> {

	@Override
	@Nullable
	public ExpressionBean apply(@Nullable Response input) {
		return input.getExpression();
	}

}
