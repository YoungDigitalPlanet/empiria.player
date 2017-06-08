/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
