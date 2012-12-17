package eu.ydp.empiria.player.client.controller.report;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportGenerator;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public interface AssessmentReportFactory {
	
	AssessmentReportProvider getAssessmentReportProvider(DataSourceDataSupplier dataSupplier, SessionDataSupplier sessionSupplier);
	
	ResultInfo getResultInfo(VariableProviderSocket variableProvider);

	HintInfo getHintInfo(VariableProviderSocket variableProvider);

	ItemReportProvider getItemReportProvider(DataSourceDataSupplier dataSupplier, SessionDataSupplier sessionSupplier, int index);
	
	AssessmentJsonReportGenerator getAssessmentJsonReportGenerator(DataSourceDataSupplier dataSupplier, SessionDataSupplier sessionSupplier);
	
}
