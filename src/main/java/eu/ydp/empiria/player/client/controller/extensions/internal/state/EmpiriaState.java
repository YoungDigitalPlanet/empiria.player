package eu.ydp.empiria.player.client.controller.extensions.internal.state;

public class EmpiriaState {

    public static final String TYPE = "type";
    public static final String STATE = "state";
    public static final String LESSON_IDENTIFIER = "lesson_identifier";

    private final EmpiriaStateType formatType;
    private final String state;
    private final String lessonIdentifier;

    public EmpiriaState(EmpiriaStateType formatType, String state, String lessonIdentifier) {
        this.formatType = formatType;
        this.state = state;
        this.lessonIdentifier = lessonIdentifier;
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

    public String getLessonIdentifier() {
        return lessonIdentifier;
    }
}
