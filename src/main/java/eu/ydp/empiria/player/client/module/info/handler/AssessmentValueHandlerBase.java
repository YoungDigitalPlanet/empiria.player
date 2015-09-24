package eu.ydp.empiria.player.client.module.info.handler;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.report.table.extraction.PagesRangeExtractor;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorage;
import eu.ydp.empiria.player.client.style.ModuleStyle;

import java.util.List;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_INFO_TEST_INCLUDE;

public abstract class AssessmentValueHandlerBase {

    private final AssessmentVariableStorage assessmentVariableStorage;
    private final List<Integer> pagesToInclude;

    public AssessmentValueHandlerBase(ModuleStyle moduleStyle, AssessmentVariableStorage assessmentVariableStorage, PagesRangeExtractor pagesRangeExtractor) {
        this.assessmentVariableStorage = assessmentVariableStorage;
        pagesToInclude = Lists.newArrayList();

        if (moduleStyle.containsKey(EMPIRIA_INFO_TEST_INCLUDE)) {
            String styleValue = moduleStyle.get(EMPIRIA_INFO_TEST_INCLUDE);
            List<Integer> pages = pagesRangeExtractor.parseRange(styleValue);
            pagesToInclude.addAll(pages);
        }
    }

    protected int getVariableValue(String valueName) {
        if (pagesToInclude.isEmpty()) {
            return assessmentVariableStorage.getVariableIntValue(valueName);
        } else {
            return assessmentVariableStorage.getVariableIntValue(valueName, pagesToInclude);
        }
    }
}
