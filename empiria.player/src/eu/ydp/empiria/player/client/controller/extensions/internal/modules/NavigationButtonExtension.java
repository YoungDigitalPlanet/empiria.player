package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import static eu.ydp.empiria.player.client.util.MapCreator.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.test.NavigationButton;
import eu.ydp.empiria.player.client.module.test.NavigationButtonDirection;

public abstract class NavigationButtonExtension extends ModuleExtension implements
						ModuleConnectorExtension, FlowRequestSocketUserExtension, 
						DataSourceDataSocketUserExtension, FlowDataSocketUserExtension, 
						DeliveryEventsListenerExtension {
	
	private static final Map<String, NavigationButtonDirection> NODE2DIRECTION = m(new HashMap<String, NavigationButtonDirection>()).
							p("nextItemNavigation", NavigationButtonDirection.NEXT).
							p("prevItemNavigation", NavigationButtonDirection.PREVIOUS);

	private FlowRequestInvoker flowRequestInvoker;

	private List<DeliveryEventsListener> deliveryListeners;
	
	private DataSourceDataSupplier dataSourceSupplier;
	
	private FlowDataSupplier flowDataSupplier;

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
	
	@Override
	public void onDeliveryEvent(DeliveryEvent flowEvent) {
		if (deliveryListeners != null) {
			for (DeliveryEventsListener listener : deliveryListeners)
				listener.onDeliveryEvent(flowEvent);
		}

		if (flowEvent.getType() == DeliveryEventType.PAGE_UNLOADED)
			removeAllDeliveryListeners();
	}

	@Override
	public ModuleCreator getModuleCreator() {
		return new ModuleCreator() {

			@Override
			public boolean isMultiViewModule() {
				return false;
			}

			@Override
			public boolean isInlineModule() {
				return false;
			}

			@Override
			public IModule createModule() {
				NavigationButton button = new NavigationButton(
													flowRequestInvoker,
													dataSourceSupplier,
													flowDataSupplier,
													getDirection(getModuleNodeName()));

				if (button instanceof DeliveryEventsListener)
					addDeliveryListener((DeliveryEventsListener) button);

				return button;
			}
		};
	}

	@Override
	public abstract String getModuleNodeName();

	private void removeAllDeliveryListeners() {
		if (deliveryListeners != null)
			deliveryListeners.clear();
	}

	private void addDeliveryListener(DeliveryEventsListener listener) {
		if (deliveryListeners == null)
			deliveryListeners = new ArrayList<DeliveryEventsListener>();

		deliveryListeners.add(listener);
	}
	
	private NavigationButtonDirection getDirection(String name){
		return NODE2DIRECTION.get(name);
	}

}
