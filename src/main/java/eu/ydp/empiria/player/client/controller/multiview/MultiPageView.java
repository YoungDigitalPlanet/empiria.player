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

package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class MultiPageView extends FlowPanel implements ResizeHandler {
    private MultiPageController controller;

    public MultiPageView() {
        Window.addResizeHandler(this);
    }

    public void setController(MultiPageController controller) {
        this.controller = controller;
    }

    @Override
    protected void onAttach() {
        setSwipeDisabled(controller.isSwipeDisabled());
        super.onAttach();
    }

    public void setSwipeDisabled(boolean swipeDisabled) {
        Style style = controller.getStyle();
        Style elementStyle = getElement().getStyle();

        style.setWidth(controller.getWidth(), Unit.PCT);

        if (swipeDisabled) {
            style.clearPosition();
            style.clearTop();
            style.clearLeft();
            elementStyle.clearPosition();
        } else {
            style.setPosition(Position.ABSOLUTE);
            style.setTop(0, Unit.PX);
            style.setLeft(0, Unit.PX);
            elementStyle.setPosition(Position.RELATIVE);
        }

        setSwipeLength();
    }

    @Override
    public void onResize(ResizeEvent event) {
        setSwipeLength();
    }

    private void setSwipeLength() {
        if (controller != null) {
            controller.setSwipeLength(RootPanel.get().getOffsetWidth() / 5);
        }
    }
}
