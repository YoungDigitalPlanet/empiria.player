package eu.ydp.empiria.player.client.module.ordering.structure;

public enum OrderInteractionOrientation {
    VERTICAL("y"), HORIZONTAL("x");
    private final String axis;

    private OrderInteractionOrientation(String axis) {
        this.axis = axis;
    }

    public String getAxis() {
        return axis;
    }
}
