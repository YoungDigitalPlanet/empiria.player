package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.common.collect.Maps;
import com.google.gwt.query.client.impl.ConsoleBrowser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleBase;

import java.util.List;
import java.util.Map;

@Singleton
public class CountFeedbackProperties {
    private Map<FeedbackCondition, Integer> map = Maps.newHashMap();

    @Inject
    ConsoleBrowser console;
    @Inject
    FeedbackRegistry feedbackRegistry;

    public void add(FeedbackCondition feedbackCondition) {
        Integer count = 1;
        if (map.containsKey(feedbackCondition)) {
            count = map.get(feedbackCondition) + 1;
        }
        map.put(feedbackCondition, count);
        console.log(feedbackCondition);
        console.log(map);
    }

    public int getCount(FeedbackCondition feedbackCondition) {
        if (map.containsKey(feedbackCondition)) {
            return map.get(feedbackCondition);
        }
        console.log("not in map");

        return 0;
    }


    public void reset(IModule module) {
        List<Feedback> moduleFeedbacks = feedbackRegistry.getModuleFeedbacks(module);
        for (Feedback feedback: moduleFeedbacks){
            map.remove(feedback.getCondition());
        }
    }
}
