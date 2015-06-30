package eu.ydp.empiria.player.client.util.events.internal;

import eu.ydp.gwtutil.client.event.EventImpl;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

import java.util.HashMap;
import java.util.Map;

public class EventTypes<H, E extends Enum<E>> {
    public Map<E, Type<H, E>> types = new HashMap<E, EventImpl.Type<H, E>>();

    public Type<H, E> getType(E type) {
        if (!types.containsKey(type)) {
            types.put(type, new Type<H, E>(type));
        }
        return types.get(type);
    }

    public Type<H, E>[] getTypes(E[] types) {
        Type<H, E>[] array = new Type[types.length];
        for (int x = 0; x < types.length; ++x) {
            array[x] = getType(types[x]);
        }
        return array;
    }

}
