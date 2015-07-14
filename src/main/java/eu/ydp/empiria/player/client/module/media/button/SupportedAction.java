package eu.ydp.empiria.player.client.module.media.button;

public interface SupportedAction<T> {
    /**
     * Czy funkcjonalnosc oferowana przez dany button jest obslugiwana w przegladarce
     *
     * @return
     */
    public boolean isSupported();
}
