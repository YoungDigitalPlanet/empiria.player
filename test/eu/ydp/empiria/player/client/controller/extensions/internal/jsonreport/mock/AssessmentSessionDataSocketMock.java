package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public class AssessmentSessionDataSocketMock implements AssessmentSessionDataSocket {

	private VariableProviderSocket variableProvider;

	public void setVariableProvider(VariableProviderSocket variableProvider) {
		this.variableProvider = variableProvider;
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return null;
	}

	@Override
	public VariableProviderSocket getVariableProviderSocket() {
		return variableProvider;
	}

	@Override
	public int getTimeAssessmentTotal() {
		return 0;
	}

}
