package eu.ydp.empiria.player.client.controller.extensions.internal.state;

public class EmpiriaState {

    public static final String TYPE = "type";
    public static final String STATE = "state";

    private final EmpiriaStateType formatType;
    private final String state;

    public EmpiriaState(EmpiriaStateType formatType, String state) {
        this.formatType = formatType;
        this.state = state;
    }

    public boolean hasType(EmpiriaStateType type) {
        return formatType.equals(type);
    }

    public EmpiriaStateType getFormatType() {
        return formatType;
    }

    public String getState() {
        return state;
    }
}
