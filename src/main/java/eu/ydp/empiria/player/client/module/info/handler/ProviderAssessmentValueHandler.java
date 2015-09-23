package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorage;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ProviderAssessmentValueHandler implements FieldValueHandler  {

    private final AssessmentVariableStorage assessmentVariableStorage;

    @Inject
    public ProviderAssessmentValueHandler(AssessmentVariableStorage assessmentVariableStorage) {
        this.assessmentVariableStorage = assessmentVariableStorage;
    }

    @Override
    public String getValue(ContentFieldInfo info, int refItemIndex) {
        int variableIntValue = assessmentVariableStorage.getVariableIntValue(info.getValueName());
        return String.valueOf(variableIntValue);
    }
}
