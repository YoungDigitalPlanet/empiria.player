package eu.ydp.empiria.player.client.controller.delivery;

import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.module.IStatefulString;

public interface DeliveryEngineSocket extends IStatefulString {

	public void setFlowOptions(FlowOptions o);
	public void setDisplayOptions(DisplayOptions o);
	public String getEngineMode();
}
