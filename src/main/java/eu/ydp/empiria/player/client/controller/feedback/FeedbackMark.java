package eu.ydp.empiria.player.client.controller.feedback;

public enum FeedbackMark {
    ALL_OK("allOk"), OK("ok"), WRONG("wrong"), DEFAULT("");

    private String name;

    private FeedbackMark(String name) {
        this.name = name;
    }

    public static FeedbackMark getMark(FeedbackProperties properties) {

        for(FeedbackMark mark : values()) {
            FeedbackPropertyName propertyName = FeedbackPropertyName.getPropertyName(mark.name);

            if(properties.getBooleanProperty(propertyName)) {
                return mark;
            }
        }

        return DEFAULT;
    }
}
