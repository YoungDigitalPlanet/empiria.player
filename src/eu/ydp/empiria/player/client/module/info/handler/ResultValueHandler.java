package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ResultValueHandler implements FieldValueHandler {
	
	private VariableProviderSocket variableProvider;
	
	@Inject
	public ResultValueHandler(@Assisted VariableProviderSocket variableProvider){
		this.variableProvider = variableProvider;
	}

	@Override
	public String getValue(ContentFieldInfo info, int refItemIndex) {
		VariableResult result = new VariableResult(variableProvider);
		return String.valueOf(result.getResult());
	}

}
