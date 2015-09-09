package eu.ydp.empiria.player.client.controller.feedback.counter.event;

import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;

public class FeedbackCounterEvent extends AbstractEvent<FeedbackCounterEventHandler, FeedbackCounterEventTypes> {
    public static EventTypes<FeedbackCounterEventHandler, FeedbackCounterEventTypes> types = new EventTypes<>();
    private final IModule module;

    public FeedbackCounterEvent(FeedbackCounterEventTypes type, IModule module) {
        super(type, module);
        this.module = module;
    }

    public IModule getSourceModule(){
        return module;
    }

    @Override
    protected EventTypes<FeedbackCounterEventHandler, FeedbackCounterEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(FeedbackCounterEventHandler handler) {
        handler.onFeedbackCounterEvent(this);
    }

    public static Type<FeedbackCounterEventHandler, FeedbackCounterEventTypes> getType(FeedbackCounterEventTypes type) {
        return types.getType(type);
    }
}
