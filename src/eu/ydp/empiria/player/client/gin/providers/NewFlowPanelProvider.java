package eu.ydp.empiria.player.client.gin.providers;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.ui.GWTPanelFactoryImpl;

public class NewFlowPanelProvider implements Provider<FlowPanel> {

	@Inject
	private GWTPanelFactoryImpl panelFactoryImpl;
	
	@Override
	public FlowPanel get() {
		return panelFactoryImpl.getFlowPanel();
	}

}
