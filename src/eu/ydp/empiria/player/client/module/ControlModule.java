package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public abstract class ControlModule extends SimpleModuleBase implements DeliveryEventsListener {

	protected FlowRequestInvoker flowRequestInvoker;

	protected DataSourceDataSupplier dataSourceSupplier;

	protected FlowDataSupplier flowDataSupplier;

	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

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
