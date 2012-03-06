package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;

public interface IControlModule extends DeliveryEventsListener {
	
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier);
	
	public void setFlowDataSupplier(FlowDataSupplier supplier);
	
	public void setFlowRequestsInvoker(FlowRequestInvoker fri);
}
