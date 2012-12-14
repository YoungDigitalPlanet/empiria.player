package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ProviderValueHandler implements FieldValueHandler {
	
	private static final String DEFAULT_VALUE = "0";
	
	private VariableProviderSocket variableProvider;
	
	@Inject
	public ProviderValueHandler(@Assisted VariableProviderSocket variableProvider){
		this.variableProvider = variableProvider;
	}

	@Override
	public String getValue(ContentFieldInfo info, int refItemIndex) {
		VariableUtil util = new VariableUtil(variableProvider);
		return util.getVariableValue(info.getValueName(), DEFAULT_VALUE);
	}
}
