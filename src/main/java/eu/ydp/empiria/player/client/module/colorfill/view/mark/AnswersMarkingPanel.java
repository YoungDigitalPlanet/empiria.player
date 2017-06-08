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

package eu.ydp.empiria.player.client.module.colorfill.view.mark;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

import java.util.List;

public class AnswersMarkingPanel extends AbsolutePanel {

    private String markStyle;

    public AnswersMarkingPanel() {
        setSize("100%", "100%");
        hide();
    }

    public void setMarkStyle(String markStyle) {
        this.markStyle = markStyle;
    }

    public void markAndShow(List<Area> points) {
        markPoints(points);
        show();
    }

    public void clearAndHide() {
        clear();
        hide();
    }

    public void markPoints(List<Area> points) {
        for (Area point : points) {
            addMarkingPoint(point);
        }
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    private void addMarkingPoint(Area point) {
        FlowPanel pointPanel = new FlowPanel();
        pointPanel.setStyleName(markStyle);
        add(pointPanel, point.getX(), point.getY());
    }

}
