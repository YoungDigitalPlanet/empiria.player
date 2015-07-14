package eu.ydp.empiria.player.client.util;

public class UniqueIdGenerator {
    private static int counter = 0;

    public String createUniqueId() {
        return "uId" + (++counter);
    }
}
