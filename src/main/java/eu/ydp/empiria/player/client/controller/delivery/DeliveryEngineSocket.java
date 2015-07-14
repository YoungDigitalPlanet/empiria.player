package eu.ydp.empiria.player.client.controller.delivery;

import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;

public interface DeliveryEngineSocket extends IStatefulString {

    public void setFlowOptions(FlowOptions o);

    public void setDisplayOptions(DisplayOptions o);

    public String getEngineMode();

    public void setInitialItemIndex(Integer num);
}
