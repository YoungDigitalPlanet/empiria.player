package eu.ydp.empiria.player.client.media.texttrack;

public class TextTrackCue implements Comparable<TextTrackCue> {
    private final String elementId;
    private final String text;
    private final double endTime;
    private final double startTime;
    private TextTrack textTrack;

    public TextTrackCue(String elementId, String startTime, String endTime, String text) {
        this(elementId, Double.valueOf(startTime), Double.valueOf(endTime), text);
    }

    public TextTrackCue(String elementId, double startTime, double endTime, String text) {
        this.elementId = elementId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }

    protected void setTextTrack(TextTrack textTrack) {
        this.textTrack = textTrack;
    }

    public String getElementId() {
        return elementId;
    }

    public String getText() {
        return text;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public TextTrack getTextTrack() {
        return textTrack;
    }

    @Override
    @SuppressWarnings("PMD")
    public int compareTo(TextTrackCue object) {
        if (startTime > object.startTime) {
            return 1;
        } else if (startTime < object.startTime) {
            return -1;
        }
        return 0;
    }

}
