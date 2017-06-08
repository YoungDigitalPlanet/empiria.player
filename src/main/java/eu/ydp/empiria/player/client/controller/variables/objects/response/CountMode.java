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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;

public enum CountMode {
    SINGLE("qp-countmode", EmpiriaStyleNameConstants.EMPIRIA_COUNTMODE_SINGLE), CORRECT_ANSWERS("qp-countmode",
            EmpiriaStyleNameConstants.EMPIRIA_COUNTMODE_CORRECT_ANSWERS);

    private String attributeName;
    private String globalCssClassName;

    CountMode(String globalCssClassName, String attributeName) {
        this.attributeName = attributeName;
        this.globalCssClassName = globalCssClassName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getGlobalCssClassName() {
        return globalCssClassName;
    }
}
