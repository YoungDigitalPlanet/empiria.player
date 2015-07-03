package eu.ydp.empiria.player.client.util.events.internal.reset;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;

public class LessonResetEvent extends AbstractEvent<LessonResetEventHandler, LessonResetEventTypes> {
    public static EventTypes<LessonResetEventHandler, LessonResetEventTypes> types = new EventTypes<>();

    public LessonResetEvent(LessonResetEventTypes type) {
        super(type, null);
    }

    @Override
    protected EventTypes<LessonResetEventHandler, LessonResetEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(LessonResetEventHandler handler) {
        handler.onLessonReset(this);
    }

    public static Type<LessonResetEventHandler, LessonResetEventTypes> getType(LessonResetEventTypes type) {
        return types.getType(type);
    }

}
