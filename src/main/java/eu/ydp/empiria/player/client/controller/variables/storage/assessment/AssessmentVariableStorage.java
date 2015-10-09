package eu.ydp.empiria.player.client.controller.variables.storage.assessment;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.communication.sockets.JsSocketHolder;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemsCollectionSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.collections.CollectionsUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
public class AssessmentVariableStorage extends VariableProviderBase implements JsSocketHolder {

    private final Set<String> identifiers;
    private ItemsCollectionSessionDataSocket itemsCollectionSessionDataSocket;

    public AssessmentVariableStorage() {
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
            List<Integer> itemsIndexes = getAllItemsIndexes();
            int value = getOutcomeVariableAssessmentTotal(identifier, itemsIndexes);
            return new Outcome(identifier, Cardinality.SINGLE, String.valueOf(value));
        }
        return null;
    }

    public int getVariableIntValue(String identifier) {
        List<Integer> itemsToInclude = getAllItemsIndexes();
        return getVariableIntValue(identifier, itemsToInclude);
    }

    public int getVariableIntValue(String identifier, List<Integer> itemsToInclude) {
        if (identifiers.contains(identifier)) {
            return getOutcomeVariableAssessmentTotal(identifier, itemsToInclude);
        }
        return 0;
    }

    protected int getOutcomeVariableAssessmentTotal(String identifier, List<Integer> itemsToInclude) {
        int total = 0;
        Variable currVar;
        String currValue;
        int currValueInt;
        for (int i : itemsToInclude) {
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

    private List<Integer> getAllItemsIndexes() {
        int itemsCount = itemsCollectionSessionDataSocket.getItemSessionDataSocketsCount();
        return CollectionsUtil.getRangeList(0, itemsCount);
    }
}
