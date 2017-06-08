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

package eu.ydp.empiria.player.client.view.item;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;

public class ItemBodyView extends FlowPanel {

    protected WidgetWorkflowListener widgetWorkflowListener;

    public ItemBodyView(WidgetWorkflowListener wwl) {
        widgetWorkflowListener = wwl;

        setStyleName("qp-text-module");
        this.sinkEvents(Event.ONCHANGE);
        this.sinkEvents(Event.ONMOUSEDOWN);
        this.sinkEvents(Event.ONMOUSEUP);
        this.sinkEvents(Event.ONMOUSEMOVE);
        this.sinkEvents(Event.ONMOUSEOUT);
    }

    public void init(Widget itemBodyWidget) {
        add(itemBodyWidget);
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

}
