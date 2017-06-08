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

package eu.ydp.empiria.player.client.module.pageswitch;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeTestClient;
import eu.ydp.empiria.player.client.module.core.base.ControlModule;
import eu.ydp.empiria.player.client.module.core.base.ISimpleModule;

public class PageSwitchModule extends ControlModule implements ISimpleModule, ChangeHandler, WorkModeTestClient {

    protected IPageSwitchWidget switchWidget;

    @Override
    public void initModule(Element element) {
    }

    @Override
    public Widget getView() {
        if (switchWidget == null) {
            switchWidget = new PageSwitchListBox();
            switchWidget.addChangeHandler(this);
            switchWidget.setItemsCount(dataSourceSupplier.getItemsCount());

            ((Widget) switchWidget).setStyleName(getStyleName());
        }

        return (Widget) switchWidget;
    }

    @Override
    public void onChange(ChangeEvent event) {
        flowRequestInvoker.invokeRequest(new FlowRequest.NavigateGotoItem(switchWidget.getCurrentIndex()));
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent flowEvent) {
        if (flowEvent.getType() == DeliveryEventType.TEST_PAGE_LOADED) {
            switchWidget.setCurrentIndex(flowDataSupplier.getCurrentPageIndex());
        }
    }

    protected String getStyleName() {
        return "qp-page-counter-list";
    }

    @Override
    public void enableTestMode() {
        switchWidget.disable();
    }

    @Override
    public void disableTestMode() {
        switchWidget.enable();
    }
}
