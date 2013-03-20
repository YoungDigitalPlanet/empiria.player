package eu.ydp.empiria.player.client.controller.report;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.CHECKS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.RESET;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.SHOW_ANSWERS;

public class HintInfo {
	
	private static final int DEFAULT_INT_VALUE = 0;
	private VariableUtil util;

	@Inject
	public HintInfo(@Assisted VariableProviderSocket variableProvider){
		util = new VariableUtil(variableProvider);
	}
	
	public int getChecks() {
		return util.getVariableIntValue(CHECKS, DEFAULT_INT_VALUE);
	}

	public int getMistakes() {
		return util.getVariableIntValue(VariableName.MISTAKES.toString(), DEFAULT_INT_VALUE);
	}

	public int getShowAnswers() {
		return util.getVariableIntValue(SHOW_ANSWERS, DEFAULT_INT_VALUE);
	}

	public int getReset() {
		return util.getVariableIntValue(RESET, DEFAULT_INT_VALUE);
	}

}
