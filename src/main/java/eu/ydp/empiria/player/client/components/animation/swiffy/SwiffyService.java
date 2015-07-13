package eu.ydp.empiria.player.client.components.animation.swiffy;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Map;

public class SwiffyService {

    @Inject
    private Provider<SwiffyObject> swiffyObjectProvider;

    private final Map<String, SwiffyObject> swiffyObjects = Maps.newHashMap();

    public SwiffyObject getSwiffyObject(String swifyName, String url) {
        SwiffyObject swiffyObject = swiffyObjectProvider.get();
        swiffyObject.setAnimationUrl(url);
        swiffyObjects.put(swifyName, swiffyObject);
        return swiffyObject;
    }

    public void clear(String swiffyName) {
        if (swiffyObjects.containsKey(swiffyName)) {
            swiffyObjects.remove(swiffyName).destroy();
        }
    }
}
