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

package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.extensions.types.*;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.core.base.ControlModule;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;

import java.util.ArrayList;
import java.util.List;

public abstract class ControlModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension, FlowRequestSocketUserExtension,
        DataSourceDataSocketUserExtension, FlowDataSocketUserExtension, DeliveryEventsListenerExtension {

    protected FlowRequestInvoker flowRequestInvoker;

    protected List<DeliveryEventsListener> deliveryListeners;

    protected DataSourceDataSupplier dataSourceSupplier;

    protected FlowDataSupplier flowDataSupplier;

    @Override
    public void onDeliveryEvent(DeliveryEvent flowEvent) {
        if (deliveryListeners != null) {
            for (DeliveryEventsListener listener : deliveryListeners)
                listener.onDeliveryEvent(flowEvent);
        }

        // TODO: usuwanie tylko wtedy gdy jest usuwany moduł
        // if (flowEvent.getType() == DeliveryEventType.PAGE_UNLOADED)
        // removeAllDeliveryListeners();
    }

    @Override
    public void setFlowDataSupplier(FlowDataSupplier supplier) {
        flowDataSupplier = supplier;
    }

    @Override
    public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
        dataSourceSupplier = supplier;
    }

    @Override
    public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
        flowRequestInvoker = fri;
    }

    @Override
    public abstract ModuleCreator getModuleCreator();

    @Override
    public abstract String getModuleNodeName();

    protected void initializeModule(IModule module) {
        if (module instanceof ControlModule) {
            ControlModule ctrlModule = (ControlModule) module;

            ctrlModule.setDataSourceDataSupplier(dataSourceSupplier);
            ctrlModule.setFlowDataSupplier(flowDataSupplier);
            ctrlModule.setFlowRequestsInvoker(flowRequestInvoker);

            addDeliveryListener(ctrlModule);
        } else if (module instanceof DeliveryEventsListener) {
            addDeliveryListener((DeliveryEventsListener) module);
        }

    }

    protected void removeAllDeliveryListeners() {
        if (deliveryListeners != null)
            deliveryListeners.clear();
    }

    private void addDeliveryListener(DeliveryEventsListener listener) {
        if (deliveryListeners == null)
            deliveryListeners = new ArrayList<DeliveryEventsListener>();

        deliveryListeners.add(listener);
    }

}
