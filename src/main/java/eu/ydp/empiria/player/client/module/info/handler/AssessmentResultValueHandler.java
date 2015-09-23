package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorage;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class AssessmentResultValueHandler implements FieldValueHandler {

    private final AssessmentVariableStorage assessmentVariableStorage;

    @Inject
    public AssessmentResultValueHandler(AssessmentVariableStorage assessmentVariableStorage) {
        this.assessmentVariableStorage = assessmentVariableStorage;
    }

    @Override
    public String getValue(ContentFieldInfo info, int refItemIndex) {
        int todo = assessmentVariableStorage.getVariableIntValue(VariableName.TODO.toString());
        int done = assessmentVariableStorage.getVariableIntValue(VariableName.DONE.toString());

        int result = 0;
        if (todo != 0) {
            result = done * 100 / todo;
        }
        return String.valueOf(result);
    }
}
