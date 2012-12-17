package eu.ydp.empiria.player.client.controller.report;

import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.TODO;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;

public class ResultInfo {
	
	private static final int DEFAULT_INT_VALUE = 0;
	
	private VariableUtil util;

	@Inject
	public ResultInfo(@Assisted VariableProviderSocket variableProvider){
		util = new VariableUtil(variableProvider);
	}

	public int getTodo() {
		return util.getVariableIntValue(TODO, DEFAULT_INT_VALUE);
	}

	public int getDone() {
		return util.getVariableIntValue(DONE, DEFAULT_INT_VALUE);
	}

	public int getErrors() {
		return util.getVariableIntValue(ERRORS, DEFAULT_INT_VALUE);
	}
	
	public int getResult(){
		VariableResult variableResult = new VariableResult(getDone(), getTodo());
		return variableResult.getResult();
	}
	
}
