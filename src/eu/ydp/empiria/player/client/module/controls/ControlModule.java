package eu.ydp.empiria.player.client.module.controls;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.IControlModule;

public abstract class ControlModule implements IControlModule {
	
	protected FlowRequestInvoker flowRequestInvoker;
	
	protected DataSourceDataSupplier dataSourceSupplier;
	
	protected FlowDataSupplier flowDataSupplier;

	@Override
	public abstract void onDeliveryEvent(DeliveryEvent flowEvent);

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		dataSourceSupplier = supplier;
	}

	@Override
	public void setFlowDataSupplier(FlowDataSupplier supplier) {
		flowDataSupplier = supplier;
	}

	@Override
	public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
		flowRequestInvoker = fri;
	}

}
