package eu.ydp.empiria.player.client.module.model.media;

public enum MimeType {

    VIDEO_MPEG("video/mpeg"), VIDEO_MP4("video/mp4"), VIDEO_OGG("video/ogg"), VIDEO_WEBM("video/webm"), UNKNOWN("unknown");

    private final String value;

    private MimeType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static MimeType fromValue(String value) {
        for (MimeType type : MimeType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
