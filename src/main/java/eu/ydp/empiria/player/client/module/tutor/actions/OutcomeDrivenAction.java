package eu.ydp.empiria.player.client.module.tutor.actions;

import eu.ydp.empiria.player.client.module.tutor.ActionType;

public interface OutcomeDrivenAction {

    boolean actionOccured();

    ActionType getActionType();
}
