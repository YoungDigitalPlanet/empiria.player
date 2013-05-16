package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.logging.Logger;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.processor.module.expression.ExpressionModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.multiple.MultipleModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ordering.OrderingModeVariableProcessor;

public class VariableProcessorFactory {

	private static final Logger LOGGER = Logger.getLogger(VariableProcessorFactory.class.getName());

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
		} else {
			return multipleModeVariableProcessor;
		}
	}
}
