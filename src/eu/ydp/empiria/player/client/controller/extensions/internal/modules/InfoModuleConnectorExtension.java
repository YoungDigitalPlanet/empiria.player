package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import java.util.ArrayList;
import java.util.List;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.info.InfoModule;
import eu.ydp.empiria.player.client.module.info.InfoModuleUnloadListener;

public class InfoModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension, DataSourceDataSocketUserExtension, SessionDataSocketUserExtension, FlowDataSocketUserExtension, DeliveryEventsListenerExtension {

	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected SessionDataSupplier sessionDataSupplier;
	protected FlowDataSupplier flowDataSupplier;
	protected List<InfoModule> modules;

	public InfoModuleConnectorExtension(){
		modules = new ArrayList<InfoModule>();
	}

	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator(false,true) {
			@Override
			public IModule createModule() {
				final InfoModule infoModule = new InfoModule(dataSourceDataSupplier, sessionDataSupplier, flowDataSupplier);
				infoModule.setModuleUnloadListener(new InfoModuleUnloadListener() {

					@Override
					public void moduleUnloaded() {
						modules.remove(infoModule);
					}
				});
				modules.add(infoModule);
				return infoModule;
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.INFO.tagName();
	}

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		dataSourceDataSupplier = supplier;
	}

	@Override
	public void setSessionDataSupplier(SessionDataSupplier supplier) {
		sessionDataSupplier = supplier;
	}

	@Override
	public void setFlowDataSupplier(FlowDataSupplier supplier) {
		flowDataSupplier = supplier;
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent event) {
		if (event.getType() == DeliveryEventType.TEST_PAGE_LOADED){
			for (InfoModule im : modules){
				im.update();
			}
		}
	}

}
