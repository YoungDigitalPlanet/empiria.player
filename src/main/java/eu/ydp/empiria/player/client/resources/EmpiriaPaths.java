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

package eu.ydp.empiria.player.client.resources;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class EmpiriaPaths {

    @Inject
    DataSourceManager dataSourceManager;
    private static final String SEPARATOR = "/";

    private String getScriptPath() {
        ItemData itemData = dataSourceManager.getItemData(0);
        return ensureTrailingSlash(itemData.getData().getBaseURL());
    }

    private String getMediaPath() {
        String scriptPath = getScriptPath();
        String mediaPath = scriptPath + "media";
        return ensureTrailingSlash(mediaPath);
    }

    public String getMediaFilePath(String filename) {
        return getMediaPath() + filename;
    }

    public String getBasePath() {
        AssessmentData assessmentData = dataSourceManager.getAssessmentData();
        XmlData data = assessmentData.getData();
        return ensureTrailingSlash(data.getBaseURL());
    }

    public String getCommonsPath() {
        String baseURL = getBasePath();
        String commonsPath = baseURL + "common";
        return ensureTrailingSlash(commonsPath);
    }

    public String getPathRelativeToCommons(String path) {
        String commonsPath = getCommonsPath();
        String relativePath = commonsPath + path;
        return ensureTrailingSlash(relativePath);
    }

    public String getCommonsFilePath(String filename) {
        String commonsPath = getCommonsPath();
        return commonsPath + filename;
    }

    private String ensureTrailingSlash(String path) {
        if (!path.endsWith(SEPARATOR)) {
            path += SEPARATOR;
        }
        return path;
    }
}
