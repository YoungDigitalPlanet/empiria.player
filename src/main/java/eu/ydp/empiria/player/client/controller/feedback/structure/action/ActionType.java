package eu.ydp.empiria.player.client.controller.feedback.structure.action;

public enum ActionType {
    NARRATION, VIDEO, POPUP, IMAGE;

    public boolean equalsToString(String value) {
        boolean equals = false;

        if (this.getName().equals(value)) {
            equals = true;
        }

        return equals;
    }

    public String getName() {
        return toString().toLowerCase();
    }
}
