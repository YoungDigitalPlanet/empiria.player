package eu.ydp.empiria.player.client.inject;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class Instance<T> {
    private final Provider<T> provider;
    private T object = null;

    @Inject
    public Instance(Provider<T> provider) {
        this.provider = provider;
    }

    public T get() {
        if (object == null) {
            object = provider.get();
        }
        return object;
    }

}
