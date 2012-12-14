package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public interface FieldValueHandlerFactory {
	
	ProviderValueHandler getProviderValueHandler(VariableProviderSocket variableProvider);
	
	TitleValueHandler getTitleValueHandler(DataSourceDataSupplier dataSupplier);
	
	ItemIndexValueHandler getItemIndexValueHandler();

	PageCountValueHandler getPageCountValueHandler(DataSourceDataSupplier dataSourceDataSupplier);

	ResultValueHandler getResultValueHandler(VariableProviderSocket variableProvider);
}
