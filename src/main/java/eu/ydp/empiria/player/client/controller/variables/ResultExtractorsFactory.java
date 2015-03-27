package eu.ydp.empiria.player.client.controller.variables;

import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.info.handler.ResultForPageIndexProvider;

public interface ResultExtractorsFactory {
	VariableResult createVariableResult(VariableProviderSocket variableProviderSocket);

	ResultForPageIndexProvider createResultForPageIndexProvider(SessionDataSupplier sessionDataSupplier);
}
