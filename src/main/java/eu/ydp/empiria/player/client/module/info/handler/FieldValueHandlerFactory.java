package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public interface FieldValueHandlerFactory {

    ProviderValueHandler getProviderValueHandler(SessionDataSupplier sessionDataSupplier);

    TitleValueHandler getTitleValueHandler(DataSourceDataSupplier dataSupplier);

    ItemIndexValueHandler getItemIndexValueHandler();

    PageCountValueHandler getPageCountValueHandler(DataSourceDataSupplier dataSourceDataSupplier);

    ResultValueHandler getResultValueHandler(SessionDataSupplier sessionDataSupplier);

    ProviderAssessmentValueHandler getProviderAssessmentValueHandler(VariableProviderSocket assessmentVariableProvider);

    AssessmentResultValueHandler getAssessmentResultValueHandler(VariableProviderSocket assessmentVariableProvider);

    FeedbackValueHandler getFeedbackValueHandler(ResultForPageIndexProvider resultForPageIndexProvider, DataSourceDataSupplier dataSourceDataSupplier);
}
