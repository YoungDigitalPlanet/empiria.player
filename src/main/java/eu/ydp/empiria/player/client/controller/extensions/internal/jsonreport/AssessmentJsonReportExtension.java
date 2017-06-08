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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;

public class AssessmentJsonReportExtension extends InternalExtension implements PlayerJsObjectModifierExtension {

    @Inject
    private AssessmentJsonReportGenerator generator;

    private JavaScriptObject playerJsObject;

    @Override
    public void setPlayerJsObject(JavaScriptObject playerJsObject) {
        this.playerJsObject = playerJsObject;
    }

    @Override
    public void init() {
        initPlayerJsObject(playerJsObject);
    }

    private native void initPlayerJsObject(JavaScriptObject player)/*-{
        var instance = this;

        player.getLessonJSONReport = function () {
            return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportExtension::getJSONReport()();
        }
    }-*/;

    private String getJSONReport() {
        AssessmentJsonReport jsonReport = generator.generate();
        return jsonReport.getJSONString();
    }
}
