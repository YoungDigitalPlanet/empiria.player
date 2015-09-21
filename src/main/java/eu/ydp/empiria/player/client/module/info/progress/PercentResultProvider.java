package eu.ydp.empiria.player.client.module.info.progress;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.info.ContentFieldRegistry;

public class PercentResultProvider {

    private static final String ITEM_RESULT_KEY = "$[item.result]";
    private static final String TEST_RESULT_KEY = "$[test.result]";

    private final ContentFieldRegistry fieldRegistry;

    @Inject
    public PercentResultProvider(ContentFieldRegistry fieldRegistry) {
        this.fieldRegistry = fieldRegistry;
    }

    public int getItemResult(int refItemIndex) {
        return getVariableIntValue(ITEM_RESULT_KEY, refItemIndex);
    }

    public int getTestResult() {
        return getVariableIntValue(TEST_RESULT_KEY, 0);
    }

    private int getVariableIntValue(String variableName, int itemIndex) {
        Optional<ContentFieldInfo> fieldInfo = fieldRegistry.getFieldInfo(variableName);
        String stringPercentValue = fieldInfo.get().getValue(itemIndex);
        return Integer.valueOf(stringPercentValue);
    }
}
