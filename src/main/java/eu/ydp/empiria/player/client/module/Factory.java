package eu.ydp.empiria.player.client.module;

/**
 * Interfejs okreslajacy klasy bedace fabrykami dla typu T
 *
 * @param <T>
 */
public interface Factory<T> {
    /**
     * @return nowa instancje lub null
     */
    public T getNewInstance();
}
