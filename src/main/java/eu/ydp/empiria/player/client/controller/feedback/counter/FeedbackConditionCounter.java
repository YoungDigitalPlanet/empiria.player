package eu.ydp.empiria.player.client.controller.feedback.counter;

import com.google.common.collect.Maps;
import com.google.gwt.query.client.impl.ConsoleBrowser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.List;
import java.util.Map;

import static eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounterEvent.*;
import static eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounterEventTypes.*;

@Singleton
public class FeedbackConditionCounter implements FeedbackCounterEventHandler{
    private Map<FeedbackCondition, Integer> map = Maps.newHashMap();

    private final FeedbackRegistry feedbackRegistry;

    @Inject
    public FeedbackConditionCounter(FeedbackRegistry feedbackRegistry, EventsBus eventsBus) {
        this.feedbackRegistry = feedbackRegistry;
        eventsBus.addHandler(getType(RESET_COUNTER), this);
    }

    public void add(FeedbackCondition feedbackCondition) {
        Integer count = 1;
        if (map.containsKey(feedbackCondition)) {
            count = map.get(feedbackCondition) + 1;
        }
        map.put(feedbackCondition, count);
    }

    public int getCount(FeedbackCondition feedbackCondition) {
        if (map.containsKey(feedbackCondition)) {
            return map.get(feedbackCondition);
        }

        return 0;
    }


    @Override
    public void onFeedbackCounterEvent(FeedbackCounterEvent event) {
        IModule sourceModule = event.getSourceModule();
        reset(sourceModule);
    }

    private void reset(IModule module) {
        List<Feedback> moduleFeedbacks = feedbackRegistry.getModuleFeedbacks(module);
        for (Feedback feedback : moduleFeedbacks) {
            map.remove(feedback.getCondition());
        }
    }
}
