package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.*;

import java.util.Map;

@Singleton
public class MatcherRegistry {

    Map<Class<? extends FeedbackCondition>, FeedbackMatcher> map;

    @Inject
    private MatcherRegistryFactory factory;

    public void init() {
        map = Maps.newHashMap();
        map.put(CountConditionBean.class, factory.getCountConditionMatcher(this));
        map.put(PropertyConditionBean.class, factory.getPropertyConditionMatcher(this));
        map.put(AndConditionBean.class, factory.getAndConditionMatcher(this));
        map.put(OrConditionBean.class, factory.getOrConditionMatcher(this));
        map.put(NotConditionBean.class, factory.getNotConditionMatcher(this));
    }

    public FeedbackMatcher getMatcher(FeedbackCondition condition) {
        if (map == null) {
            init();
        }
        return map.get(condition.getClass());
    }

}
