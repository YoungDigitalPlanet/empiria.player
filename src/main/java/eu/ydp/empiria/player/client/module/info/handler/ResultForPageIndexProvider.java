package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.ResultExtractorsFactory;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;

public class ResultForPageIndexProvider {

    private ResultExtractorsFactory variableResultFactory;
    private SessionDataSupplier sessionDataSupplier;

    @Inject
    public ResultForPageIndexProvider(@Assisted SessionDataSupplier sessionDataSupplier,
                                      ResultExtractorsFactory variableResultFactory) {

        this.sessionDataSupplier = sessionDataSupplier;
        this.variableResultFactory = variableResultFactory;
    }

    public int get(int pageIndex) {
        ItemSessionDataSocket itemSessionDataSocket = sessionDataSupplier.getItemSessionDataSocket(pageIndex);
        VariableProviderSocket socket = itemSessionDataSocket.getVariableProviderSocket();
        VariableResult variableResult = variableResultFactory.createVariableResult(socket);
        return variableResult.getResult();
    }
}
