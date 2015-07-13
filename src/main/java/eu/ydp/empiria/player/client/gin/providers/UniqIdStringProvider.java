package eu.ydp.empiria.player.client.gin.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

public class UniqIdStringProvider implements Provider<String> {

    @Inject
    private UniqueIdGenerator idGenerator;

    @Override
    public String get() {
        return idGenerator.createUniqueId();
    }

}
