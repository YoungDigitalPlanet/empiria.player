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

package eu.ydp.empiria.player.client.module.core.base;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;

public abstract class ControlModule extends SimpleModuleBase implements DeliveryEventsListener {

    protected FlowRequestInvoker flowRequestInvoker;

    protected DataSourceDataSupplier dataSourceSupplier;

    protected FlowDataSupplier flowDataSupplier;

    @Override
    public abstract void onDeliveryEvent(DeliveryEvent flowEvent);

    public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
        dataSourceSupplier = supplier;
    }

    public void setFlowDataSupplier(FlowDataSupplier supplier) {
        flowDataSupplier = supplier;
    }

    public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
        flowRequestInvoker = fri;
    }

}
