package eu.ydp.empiria.player.client.controller.feedback.counter;


import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.Collection;
import java.util.List;

@Singleton
public class FeedbackNotifyCounter extends FeedbackCounter<FeedbackAction> {

    public static final Function<Feedback, List<FeedbackAction>> FEEDBACK_TO_ACTIONS_MAPPER = new Function<Feedback, List<FeedbackAction>>() {
        @Override
        public List<FeedbackAction> apply(Feedback feedback) {
            return feedback.getActions();
        }
    };

    private final FeedbackRegistry feedbackRegistry;

    @Inject
    public FeedbackNotifyCounter(EventsBus eventsBus, FeedbackRegistry feedbackRegistry) {
        super(eventsBus);
        this.feedbackRegistry = feedbackRegistry;
    }

    @Override
    protected Collection<FeedbackAction> getObjectsToRemove(IModule iModule) {
        return FluentIterable.from(feedbackRegistry.getModuleFeedbacks(iModule))
                .transformAndConcat(FEEDBACK_TO_ACTIONS_MAPPER)
                .toList();
    }
}
