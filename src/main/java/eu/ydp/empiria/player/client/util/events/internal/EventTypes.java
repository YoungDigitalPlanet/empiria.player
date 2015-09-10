package eu.ydp.empiria.player.client.util.events.internal;

import java.util.HashMap;
import java.util.Map;

public class EventTypes<H, E extends Enum<E>> {
    public Map<E, EventType<H, E>> types = new HashMap<E, EventType<H, E>>();

    public EventType<H, E> getType(E type) {
        if (!types.containsKey(type)) {
            types.put(type, new EventType<H, E>(type));
        }
        return types.get(type);
    }

    public EventType<H, E>[] getTypes(E[] types) {
        EventType<H, E>[] array = new EventType[types.length];
        for (int x = 0; x < types.length; ++x) {
            array[x] = getType(types[x]);
        }
        return array;
    }

}
