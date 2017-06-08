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

package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class GroupedAnswer {

    private String value;
    private boolean isUsed;
    private Response usedByResponse;

    public GroupedAnswer(String value) {
        this(value, false, null);
    }

    public GroupedAnswer(String value, boolean isUsed, Response usedByResponse) {
        this.value = value;
        this.isUsed = isUsed;
        this.usedByResponse = usedByResponse;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Response getUsedByResponse() {
        return usedByResponse;
    }

    public void setUsedByResponse(Response usedByResponse) {
        this.usedByResponse = usedByResponse;
    }

    public String getValue() {
        return value;
    }

}
