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
