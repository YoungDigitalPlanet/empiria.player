package eu.ydp.empiria.player.client.controller.feedback.counter;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.module.core.base.IModule;

import java.util.Collection;
import java.util.List;

public class FeedbackCounterCleaner {

    private static final Function<Feedback, List<FeedbackCountable>> FEEDBACK_TO_COUNTABLE_MAPPER = new Function<Feedback, List<FeedbackCountable>>() {
        @Override
        public List<FeedbackCountable> apply(Feedback feedback) {
            List<FeedbackCountable> feedbackCountables = Lists.newArrayList();
            feedbackCountables.addAll(feedback.getActions());
            feedbackCountables.add(feedback.getCondition());
            return feedbackCountables;
        }
    };

    private final FeedbackRegistry feedbackRegistry;

    @Inject
    public FeedbackCounterCleaner(FeedbackRegistry feedbackRegistry) {
        this.feedbackRegistry = feedbackRegistry;
    }

    public Collection<FeedbackCountable> getObjectsToRemove(IModule iModule) {
        return FluentIterable.from(feedbackRegistry.getModuleFeedbacks(iModule))
                .transformAndConcat(FEEDBACK_TO_COUNTABLE_MAPPER)
                .toList();
    }
}
