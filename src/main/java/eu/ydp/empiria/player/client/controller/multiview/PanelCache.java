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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;
import eu.ydp.empiria.player.client.view.player.AbstractElementCache;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;

@Singleton
public class PanelCache extends AbstractElementCache<KeyValue<FlowPanel, FlowPanel>> {
    @Inject
    protected PageStyleNameConstants styleNames;
    @Inject
    protected GWTPanelFactory panelFactory;

    protected SwipeType swipeType;

    private FlowPanel parent;

    private final static float WIDTH = 100;

    @Override
    protected KeyValue<FlowPanel, FlowPanel> getElement(Integer index) {
        parent = panelFactory.getFlowPanel();
        FlowPanel childPanel = panelFactory.getFlowPanel();

        Style style = parent.getElement().getStyle();
        parent.getElement().setId(styleNames.QP_PAGE() + index.intValue());

        if (swipeType != SwipeType.DISABLED) {
            style.setPosition(Position.ABSOLUTE);
            style.setTop(0, Unit.PX);
            style.setLeft(WIDTH * index, Unit.PCT);
            style.setWidth(WIDTH, Unit.PCT);
        }

        childPanel.setHeight("100%");
        childPanel.setWidth("100%");
        parent.add(childPanel);
        return new KeyValue<FlowPanel, FlowPanel>(parent, childPanel);
    }

    public void setSwipeType(SwipeType swipeType) {
        this.swipeType = swipeType;
        if (parent != null) {
            Style style = parent.getElement().getStyle();

            if (swipeType == SwipeType.DISABLED) {
                style.clearTop();
                style.clearPosition();
            } else {
                style.setTop(0, Unit.PX);
                style.setPosition(Position.ABSOLUTE);
            }
        }
    }

}
