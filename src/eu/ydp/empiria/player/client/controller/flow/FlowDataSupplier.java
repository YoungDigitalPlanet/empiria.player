package eu.ydp.empiria.player.client.controller.flow;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageType;

public interface FlowDataSupplier {

	FlowOptions getFlowOptions();
	PageType getCurrentPageType();
	int getCurrentPageIndex();
	int getPageCount();
	ActivityMode getActivityMode();
	boolean getFlowFlagCheck();
	boolean getFlowFlagShowAnswers();
	boolean getFlowFlagLock();
}
