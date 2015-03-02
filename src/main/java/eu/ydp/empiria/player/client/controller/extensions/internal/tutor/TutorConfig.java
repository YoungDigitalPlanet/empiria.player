package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static com.google.common.collect.Iterables.*;
import static eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties.fromJs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JsArray;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorActionJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorCommandJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorJs;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import eu.ydp.gwtutil.client.collections.JsArrayIterable;

public class TutorConfig {
	private final TutorConfigJs tutorConfigJs;

	public TutorConfig(TutorConfigJs tutorConfigJs) {
		this.tutorConfigJs = tutorConfigJs;
	}

	public boolean supportsAction(final ActionType type) {
		JsArray<TutorActionJs> actions = tutorConfigJs.getActions();
		ActionTypePredicate predicate = new ActionTypePredicate(type);
		return any(JsArrayIterable.create(actions), predicate);
	}

	public Iterable<TutorCommandConfig> getCommandsForAction(ActionType type) {
		Iterator<TutorActionJs> iterator = findActionJsForType(type);
		if (!iterator.hasNext()) {
			throw new IllegalArgumentException("Action type not defined in tutor configuration");
		}
		TutorActionJs actionJs = iterator.next();
		return extractActionCommands(actionJs);
	}

	private Iterable<TutorCommandConfig> extractActionCommands(TutorActionJs actionJs) {
		JsArray<TutorCommandJs> commands = actionJs.getCommands();
		JsArrayIterable<TutorCommandJs> commandsIterable = JsArrayIterable.create(commands);
		return transform(commandsIterable, new TutorCommandTransformation());
	}

	private Iterator<TutorActionJs> findActionJsForType(ActionType type) {
		JsArray<TutorActionJs> actions = tutorConfigJs.getActions();
		ActionTypePredicate predicate = new ActionTypePredicate(type);
		Iterable<TutorActionJs> actionsFiltered = filter(JsArrayIterable.create(actions), predicate);
		return actionsFiltered.iterator();
	}

	public int getTutorPersonasCount() {
		return tutorConfigJs.getTutors().length();
	}

	public TutorPersonaProperties getTutorPersonaProperties(int tutorPersonaIndex) {
		if (tutorPersonaIndex >= tutorConfigJs.getTutors().length()) {
			throw new IndexOutOfBoundsException("No persona with index " + tutorPersonaIndex + " found in tutor configuration.");
		}
		TutorJs persona = tutorConfigJs.getTutors().get(tutorPersonaIndex);
		return fromJs(persona, tutorPersonaIndex);
	}

	public List<TutorPersonaProperties> getPersonas() {
		JsArray<TutorJs> tutors = tutorConfigJs.getTutors();
		List<TutorPersonaProperties> personas = new ArrayList<TutorPersonaProperties>();
		for (int i = 0; i < tutors.length(); i++) {
			TutorPersonaProperties persona = fromJs(tutors.get(i), i);
			personas.add(persona);
		}
		return personas;
	}
}
