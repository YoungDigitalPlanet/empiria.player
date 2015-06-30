package eu.ydp.empiria.player.client.controller.flow.processing.commands;

import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public interface ActivityCommandsListener {

    void checkPage();

    void showAnswersPage();

    void continuePage();

    void resetPage();

    void lockPage();

    void unlockPage();

    void checkGroup(GroupIdentifier gi);

    void showAnswersGroup(GroupIdentifier gi);

    void continueGroup(GroupIdentifier gi);

    void resetGroup(GroupIdentifier gi);

    void lockGroup(GroupIdentifier gi);

    void unlockGroup(GroupIdentifier gi);
}
