package eu.ydp.empiria.player.client.gin.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.inject.Instance;

public class FlowDataSupplierProvider implements Provider<FlowDataSupplier> {

	@Inject
	Instance<FlowManager> flowManager;

	@Override
	public FlowDataSupplier get() {
		return flowManager.get().getFlowDataSupplier();
	}

}
