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

package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.Assessment;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class AssessmentViewCarrier {

    private final Assessment assessment;

    public AssessmentViewCarrier(Assessment assessment, ViewSocket hvs, ViewSocket fvs) {
        headerViewSocket = hvs;
        footerViewSocket = fvs;
        this.assessment = assessment;
    }

    private final ViewSocket headerViewSocket;
    private final ViewSocket footerViewSocket;

    public Widget getHeaderView() {
        return (headerViewSocket == null) ? null : headerViewSocket.getView(); // NOPMD
    }

    public Widget getSkinView() {
        return assessment.getSkinView();
    }

    public Widget getFooterView() {
        return (footerViewSocket == null) ? null : footerViewSocket.getView(); // NOPMD
    }

}
