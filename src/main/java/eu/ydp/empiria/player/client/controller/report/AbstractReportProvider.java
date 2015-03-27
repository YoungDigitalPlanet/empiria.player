package eu.ydp.empiria.player.client.controller.report;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public abstract class AbstractReportProvider {

	protected DataSourceDataSupplier dataSupplier;

	protected AssessmentReportFactory factory;

	protected SessionDataSupplier sessionSupplier;

	public AbstractReportProvider(DataSourceDataSupplier dataSupplier, SessionDataSupplier sessionSupplier, AssessmentReportFactory factory) {
		this.dataSupplier = dataSupplier;
		this.sessionSupplier = sessionSupplier;
		this.factory = factory;
	}

	abstract public String getTitle();

	public ResultInfo getResult() {
		return factory.getResultInfo(getVariableProvider());
	}

	public HintInfo getHints() {
		return factory.getHintInfo(getVariableProvider());
	}

	abstract protected VariableProviderSocket getVariableProvider();

}
