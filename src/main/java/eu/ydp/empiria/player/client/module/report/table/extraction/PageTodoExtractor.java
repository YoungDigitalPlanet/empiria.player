package eu.ydp.empiria.player.client.module.report.table.extraction;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class PageTodoExtractor {

    private final SessionDataSupplier sessionDataSupplier;

    @Inject
    public PageTodoExtractor(@Assisted SessionDataSupplier sessionDataSupplier) {
        this.sessionDataSupplier = sessionDataSupplier;
    }

    public int extract(int pageRowIndex) {
        int todo = 0;
        String value = getPageVariableValue(pageRowIndex, "TODO");

        if (value != null) {
            todo = Integer.parseInt(value);
        }

        return todo;
    }

    private String getPageVariableValue(int pageRowIndex, String variableName) {
        ItemSessionDataSocket itemDataSocket = sessionDataSupplier.getItemSessionDataSocket(pageRowIndex);
        VariableProviderSocket variableSocket = itemDataSocket.getVariableProviderSocket();
        Variable variable = variableSocket.getVariableValue(variableName);

        String outputValue = null;
        if (variable != null) {
            outputValue = variable.getValuesShort();
        }
        return outputValue;
    }
}
