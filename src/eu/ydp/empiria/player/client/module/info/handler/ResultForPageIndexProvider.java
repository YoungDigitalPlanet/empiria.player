package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;
import eu.ydp.empiria.player.client.controller.variables.VariableResultFactory;

public class ResultForPageIndexProvider {

	@Inject
	private VariableResultFactory variableResultFactory;
	private SessionDataSupplier sessionDataSupplier;

	public void setSessionDataSupplier(SessionDataSupplier sessionDataSupplier) {
		this.sessionDataSupplier = sessionDataSupplier;
	}

	public int getFor(int pageIndex) {
		ItemSessionDataSocket itemSessionDataSocket = sessionDataSupplier.getItemSessionDataSocket(pageIndex);
		VariableProviderSocket socket = itemSessionDataSocket.getVariableProviderSocket();
		VariableResult variableResult = variableResultFactory.getVariableResult(socket);
		return variableResult.getResult();
	}
}