package eu.ydp.empiria.player.client.controller.feedback.counter;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.Collection;
import java.util.List;

@Singleton
public class FeedbackConditionCounter extends FeedbackCounter<FeedbackCondition> {

    private static final Function<Feedback, FeedbackCondition> FEEDBACK_TO_CONDITION_MAPPER = new Function<Feedback, FeedbackCondition>() {
        @Override
        public FeedbackCondition apply(Feedback feedback) {
            return feedback.getCondition();
        }
    };

    private final FeedbackRegistry feedbackRegistry;

    @Inject
    public FeedbackConditionCounter(EventsBus eventsBus, FeedbackRegistry feedbackRegistry) {
        super(eventsBus);
        this.feedbackRegistry = feedbackRegistry;
    }

    @Override
    protected Collection<FeedbackCondition> getObjectsToRemove(IModule module) {
        List<Feedback> moduleFeedbacks = feedbackRegistry.getModuleFeedbacks(module);
        return Collections2.transform(moduleFeedbacks, FEEDBACK_TO_CONDITION_MAPPER);
    }
}
