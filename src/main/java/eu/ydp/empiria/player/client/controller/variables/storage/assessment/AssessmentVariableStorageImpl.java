package eu.ydp.empiria.player.client.controller.variables.storage.assessment;

import eu.ydp.empiria.player.client.controller.communication.sockets.JsSocketHolder;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemsCollectionSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.HashSet;
import java.util.Set;

public class AssessmentVariableStorageImpl extends VariableProviderBase implements JsSocketHolder {

    private final Set<String> identifiers;
    private ItemsCollectionSessionDataSocket itemsCollectionSessionDataSocket;

    public AssessmentVariableStorageImpl() {
        identifiers = new HashSet<String>();
    }

    public void init(ItemsCollectionSessionDataSocket icsds) {
        itemsCollectionSessionDataSocket = icsds;
        ensureVariables();
    }

    protected void ensureVariables() {
        ensureVariable("DONE");
        ensureVariable("TODO");
        ensureVariable("ERRORS");
        ensureVariable("CHECKS");
        ensureVariable("SHOW_ANSWERS");
        ensureVariable("RESET");
        ensureVariable("MISTAKES");
        ensureVariable("VISITED");
    }

    private void ensureVariable(String identifier) {
        if (!identifiers.contains(identifier)) {
            identifiers.add(identifier);
        }
    }

    @Override
    public Set<String> getVariableIdentifiers() {
        return identifiers;
    }

    @Override
    public Variable getVariableValue(String identifier) {
        if (identifiers.contains(identifier)) {
            int value = getOutcomeVariableAssessmentTotal(identifier);
            return new Outcome(identifier, Cardinality.SINGLE, String.valueOf(value));
        }
        return null;
    }

    protected int getOutcomeVariableAssessmentTotal(String identifier) {
        int total = 0;
        Variable currVar;
        String currValue;
        int currValueInt;
        for (int i = 0; i < itemsCollectionSessionDataSocket.getItemSessionDataSocketsCount(); i++) {
            if (itemsCollectionSessionDataSocket.getItemSessionDataSocket(i) != null) {
                currVar = itemsCollectionSessionDataSocket.getItemSessionDataSocket(i).getVariableProviderSocket().getVariableValue(identifier);
                if (currVar == null) {
                    continue;
                }
                currValue = currVar.getValuesShort();
                if (currValue != null && currValue.length() > 0) {
                    if (currValue.matches("^[0-9]+$")) {
                        currValueInt = NumberUtils.tryParseInt(currValue);
                        total += currValueInt;
                    } else if ("TRUE".equals(currValue.toUpperCase())) {
                        total += 1;
                    }
                }
            }
        }
        return total;
    }

}
