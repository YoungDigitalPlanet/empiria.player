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

package eu.ydp.empiria.player.client.view.player;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.gin.factory.AssessmentFactory;
import eu.ydp.empiria.player.client.view.assessment.AssessmentContentView;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;

@Singleton
public class PlayerContentView extends Composite implements PlayerViewSocket {
    private static PlayerContentViewUiBinder uiBinder = GWT.create(PlayerContentViewUiBinder.class);

    interface PlayerContentViewUiBinder extends UiBinder<Widget, PlayerContentView> {
    }

    private final AssessmentContentView assessmentContentView; // NOPMD

    @UiField
    protected Panel headerPanel;
    @UiField
    protected FlowPanel assessmentPanel = null;
    @UiField
    protected Panel footerPanel;

    @Inject
    public PlayerContentView(AssessmentFactory assessmentFactory) {
        initWidget(uiBinder.createAndBindUi(this));
        assessmentContentView = assessmentFactory.geAssessmentContentView(assessmentPanel);
    }

    @Override
    public void setPlayerViewCarrier(PlayerViewCarrier pvd) {
        headerPanel.clear();
        headerPanel.add(pvd.getHeaderView());
        footerPanel.clear();
        footerPanel.add(pvd.getFooterView());
    }

    @Override
    public AssessmentViewSocket getAssessmentViewSocket() {
        return assessmentContentView;
    }
}
