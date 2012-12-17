package eu.ydp.empiria.player.client.controller.report;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public interface AssessmentReportFactory {
	
	ResultInfo getResultInfo(VariableProviderSocket variableProvider);

	HintInfo getHintInfo(VariableProviderSocket variableProvider);
	
}
