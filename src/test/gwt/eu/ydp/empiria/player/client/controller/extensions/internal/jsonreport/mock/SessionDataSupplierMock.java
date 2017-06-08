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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;

import java.util.List;

public class SessionDataSupplierMock implements SessionDataSupplier {

    private AssessmentSessionDataSocket assessmentSessionDataSocket;

    private List<ItemSessionDataSocket> itemSessionDataSocketList = Lists.newArrayList();

    public void setAssessmentSessionDataSocket(AssessmentSessionDataSocket assessmentSessionDataSocket) {
        this.assessmentSessionDataSocket = assessmentSessionDataSocket;
    }

    public void setItemSessionDataSocketList(List<ItemSessionDataSocket> itemSessionDataSocketList) {
        this.itemSessionDataSocketList = itemSessionDataSocketList;
    }

    @Override
    public AssessmentSessionDataSocket getAssessmentSessionDataSocket() {
        return assessmentSessionDataSocket;
    }

    @Override
    public ItemSessionDataSocket getItemSessionDataSocket(int index) {
        return itemSessionDataSocketList.get(index);
    }

}
