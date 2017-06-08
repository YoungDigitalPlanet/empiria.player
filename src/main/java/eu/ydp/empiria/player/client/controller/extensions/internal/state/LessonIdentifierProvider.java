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

package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.gwtutil.client.Base64Util;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LessonIdentifierProvider {

    private final AssessmentDataSourceManager assessmentDataSourceManager;
    private final Base64Util base64Util;

    @Inject
    public LessonIdentifierProvider(AssessmentDataSourceManager assessmentDataSourceManager, Base64Util base64Util) {
        this.assessmentDataSourceManager = assessmentDataSourceManager;
        this.base64Util = base64Util;
    }

    public String getLessonIdentifier() {
        String title = assessmentDataSourceManager.getAssessmentTitle();
        return base64Util.encode(title);
    }
}
