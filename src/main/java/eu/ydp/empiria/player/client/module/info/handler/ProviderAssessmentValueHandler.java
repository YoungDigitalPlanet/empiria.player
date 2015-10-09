package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.report.table.extraction.PagesRangeExtractor;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorage;
import eu.ydp.empiria.player.client.gin.binding.CachedModuleScoped;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.style.ModuleStyle;

public class ProviderAssessmentValueHandler extends AssessmentValueHandlerBase implements FieldValueHandler {

    @Inject
    public ProviderAssessmentValueHandler(@CachedModuleScoped ModuleStyle moduleStyle, AssessmentVariableStorage assessmentVariableStorage, PagesRangeExtractor pagesRangeExtractor) {
        super(moduleStyle, assessmentVariableStorage, pagesRangeExtractor);
    }

    @Override
    public String getValue(ContentFieldInfo info, int refItemIndex) {
        int variableIntValue = getVariableValue(info.getValueName());
        return String.valueOf(variableIntValue);
    }
}
