package eu.ydp.empiria.player.client.controller.feedback.structure.action;

public enum ActionType {
    NARRATION, VIDEO, POPUP, IMAGE;

    public boolean equalsToString(String value) {
        return getName().equals(value);
    }

    public String getName() {
        return toString().toLowerCase();
    }
}
