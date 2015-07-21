package eu.ydp.empiria.player.client.module.media.button;

public interface SupportedAction {
    /**
     * Czy funkcjonalnosc oferowana przez dany button jest obslugiwana w przegladarce
     *
     * @return
     */
    boolean isSupported();
}
