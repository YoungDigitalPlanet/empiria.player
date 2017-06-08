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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeClient;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class AssessmentBodyView extends FlowPanel implements WorkModeClient {

    protected WidgetWorkflowListener widgetWorkflowListener;
    protected ModeStyleNameConstants modeStyleNameConstants;
    protected StyleNameConstants styleNameConstants;
    private final PlayerWorkModeService playerWorkModeService;

    @Inject
    public AssessmentBodyView(@Assisted WidgetWorkflowListener wwl, ModeStyleNameConstants modeStyleNameConstants, StyleNameConstants styleNameConstants, PlayerWorkModeService playerWorkModeService) {
        widgetWorkflowListener = wwl;
        this.modeStyleNameConstants = modeStyleNameConstants;
        this.playerWorkModeService = playerWorkModeService;
        this.styleNameConstants = styleNameConstants;
        setStyleName(styleNameConstants.QP_ASSESSMENT_VIEW());
        this.sinkEvents(Event.ONCHANGE);
        this.sinkEvents(Event.ONMOUSEDOWN);
        this.sinkEvents(Event.ONMOUSEUP);
        this.sinkEvents(Event.ONMOUSEMOVE);
        this.sinkEvents(Event.ONMOUSEOUT);
    }

    public void init(Widget assessmentBodyWidget) {
        add(assessmentBodyWidget);
        playerWorkModeService.registerModule(this);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        widgetWorkflowListener.onLoad();
    }

    @Override
    public void onUnload() {
        super.onUnload();
        widgetWorkflowListener.onUnload();
    }

    @Override
    public void enablePreviewMode() {
        addStyleName(modeStyleNameConstants.QP_MODULE_MODE_PREVIEW());
    }

    @Override
    public void enableTestMode() {
        addStyleName(modeStyleNameConstants.QP_MODULE_MODE_TEST());
    }

    @Override
    public void disableTestMode() {
        removeStyleName(modeStyleNameConstants.QP_MODULE_MODE_TEST());
    }

    @Override
    public void enableTestSubmittedMode() {
        addStyleName(modeStyleNameConstants.QP_MODULE_MODE_TEST_SUBMITTED());
    }

    @Override
    public void disableTestSubmittedMode() {
        removeStyleName(modeStyleNameConstants.QP_MODULE_MODE_TEST_SUBMITTED());
    }
}
