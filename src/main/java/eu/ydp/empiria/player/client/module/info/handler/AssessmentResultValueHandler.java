package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.report.table.extraction.PagesRangeExtractor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorage;
import eu.ydp.empiria.player.client.gin.binding.CachedModuleScoped;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.style.ModuleStyle;

public class AssessmentResultValueHandler extends AssessmentValueHandlerBase implements FieldValueHandler {

    @Inject
    public AssessmentResultValueHandler(@CachedModuleScoped ModuleStyle moduleStyle, AssessmentVariableStorage assessmentVariableStorage, PagesRangeExtractor pagesRangeExtractor) {
        super(moduleStyle, assessmentVariableStorage, pagesRangeExtractor);
    }

    @Override
    public String getValue(ContentFieldInfo info, int refItemIndex) {
        int todo = getTodo();
        int done = getDone();
        int result = 0;
        if (todo != 0) {
            result = done * 100 / todo;
        }
        return String.valueOf(result);
    }

    private int getTodo() {
        return getVariableValue(VariableName.TODO.toString());
    }

    private int getDone() {
        return getVariableValue(VariableName.DONE.toString());
    }
}
