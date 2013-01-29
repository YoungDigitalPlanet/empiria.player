package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;


public class ProviderAssessmentValueHandler extends ProviderAssessmentValueHandlerBase {
	
	private static final String DEFAULT_VALUE = "0";

	@Inject
	public ProviderAssessmentValueHandler(@Assisted VariableProviderSocket assessmentVariableProvider){
		super(assessmentVariableProvider);
	}

	@Override
	protected String countValue(ContentFieldInfo info,	VariableProviderSocket provider) {
		VariableUtil util = new VariableUtil(provider);
		return util.getVariableValue(info.getValueName(), DEFAULT_VALUE);
	}

}
