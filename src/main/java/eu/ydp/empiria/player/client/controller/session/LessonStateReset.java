package eu.ydp.empiria.player.client.controller.session;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.reset.*;

public class LessonStateReset implements LessonResetEventHandler {

	private final SessionDataManager sessionDataManager;

	@Inject
	public LessonStateReset(SessionDataManager sessionDataManager, EventsBus eventsBus) {
		this.sessionDataManager = sessionDataManager;
		eventsBus.addHandler(LessonResetEvent.getType(LessonResetEventTypes.RESET), this);

	}

	@Override
	public void onLessonReset(LessonResetEvent event) {
		sessionDataManager.resetLessonsState();
	}
}
