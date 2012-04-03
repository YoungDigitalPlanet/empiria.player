package eu.ydp.empiria.player.client.controller.flow.processing.commands;

import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public interface ActivityCommandsListener {

	public void checkPage();
	public void showAnswersPage();
	public void continuePage();
	public void resetPage();
	public void lockPage();
	public void unlockPage();

	public void checkGroup(GroupIdentifier gi);
	public void showAnswersGroup(GroupIdentifier gi);
	public void continueGroup(GroupIdentifier gi);
	public void resetGroup(GroupIdentifier gi);
	public void lockGroup(GroupIdentifier gi);
	public void unlockGroup(GroupIdentifier gi);
}
