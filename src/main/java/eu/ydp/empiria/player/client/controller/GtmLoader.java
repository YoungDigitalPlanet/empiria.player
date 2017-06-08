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

package eu.ydp.empiria.player.client.controller;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;

import javax.inject.Singleton;

@Singleton
public class GtmLoader {

    public static final String GTM_FILE_NAME = "gtm.js";
    private final EmpiriaPaths empiriaPaths;
    private final ScriptInjectorWrapper scriptInjectorWrapper;
    private final AssessmentDataSourceManager assessmentDataSourceManager;

    @Inject
    public GtmLoader(EmpiriaPaths empiriaPaths, ScriptInjectorWrapper scriptInjectorWrapper, AssessmentDataSourceManager assessmentDataSourceManager) {
        this.empiriaPaths = empiriaPaths;
        this.scriptInjectorWrapper = scriptInjectorWrapper;
        this.assessmentDataSourceManager = assessmentDataSourceManager;
    }

    public void loadGtm() {
        Optional<String> gtm = assessmentDataSourceManager.getAssessmentGtm();
        if (gtm.isPresent()) {
            String gtmURL = empiriaPaths.getCommonsFilePath(GTM_FILE_NAME);
            scriptInjectorWrapper.fromUrl(gtmURL);
        }
    }
}
