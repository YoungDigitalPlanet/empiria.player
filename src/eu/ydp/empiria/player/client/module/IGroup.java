package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.flow.GroupActivityProcessor;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public interface IGroup  extends IActivity{
	public GroupIdentifier getGroupIdentifier();
	public GroupActivityProcessor getGroupFlowProcessor();
}
