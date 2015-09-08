package eu.ydp.empiria.player.client.controller.feedback.counter;

import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.Collection;
import java.util.Map;

import static eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounterEvent.getType;
import static eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounterEventTypes.RESET_COUNTER;

public abstract class FeedbackCounter<T> implements FeedbackCounterEventHandler {
    private Map<T, Integer> map = Maps.newHashMap();

    public FeedbackCounter(EventsBus eventsBus) {
        eventsBus.addHandler(getType(RESET_COUNTER), this);
    }

    public void add(T t) {
        int count = 1;

        if (map.containsKey(t)) {
            count = map.get(t) + 1;
        }
        map.put(t, count);
    }

    public int getCount(T t) {
        if (map.containsKey(t)) {
            return map.get(t);
        }

        return 0;
    }

    @Override
    public void onFeedbackCounterEvent(FeedbackCounterEvent event) {
        IModule sourceModule = event.getSourceModule();
        Collection<T> objectsToRemove = getObjectsToRemove(sourceModule);
        for (T t : objectsToRemove) {
            map.remove(t);
        }
    }

    protected abstract Collection<T> getObjectsToRemove(IModule iModule);
}
