package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

public class ConnectorTimeResolutionToSecondsConverter {

    public static final double MILLIS_DIVISOR = 1000;

    public double toSeconds(int timeFromConnector) {
        return timeFromConnector / MILLIS_DIVISOR;
    }

    public int fromSeconds(double timeInSeconds) {
        return (int) (timeInSeconds * MILLIS_DIVISOR);
    }
}
