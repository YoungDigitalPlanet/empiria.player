package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.report.HintInfo;
import eu.ydp.empiria.player.client.controller.report.ItemReportProvider;
import eu.ydp.empiria.player.client.controller.report.ResultInfo;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public class AssessmentReportFactoryMock implements AssessmentReportFactory{

    private final SessionDataSupplierMock sessionSupplier = new SessionDataSupplierMock();

    private final DataSourceDataSupplierMock dataSupplier = new DataSourceDataSupplierMock();

    @Override
    public ResultInfo getResultInfo(VariableProviderSocket variableProvider) {
        return new ResultInfo(variableProvider);
    }

    @Override
    public HintInfo getHintInfo(VariableProviderSocket variableProvider) {
        return new HintInfo(variableProvider);
    }

    @Override
    public ItemReportProvider getItemReportProvider(int index) {
        return new ItemReportProvider(index, dataSupplier, sessionSupplier, this);
    }

    public DataSourceDataSupplierMock getDataSupplier() {
        return dataSupplier;
    }

    public SessionDataSupplierMock getSessionSupplier() {
        return sessionSupplier;
    }
}
