package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TutorPopupProvider {

	private final Provider<TutorPopupPresenter> provider;
	private final Map<String, TutorPopupPresenter> idToPresenter = new HashMap<String, TutorPopupPresenter>();

	@Inject
	public TutorPopupProvider(Provider<TutorPopupPresenter> provider) {
		this.provider = provider;
	}

	public TutorPopupPresenter get(String tutorId) {
		TutorPopupPresenter tutorPopupPresenter = idToPresenter.get(tutorId);

		if (tutorPopupPresenter == null) {
			tutorPopupPresenter = createNewTutorPopupPresenter(tutorId);
		}

		return tutorPopupPresenter;
	}

	private TutorPopupPresenter createNewTutorPopupPresenter(String tutorId) {
		TutorPopupPresenter tutorPopupPresenter;
		tutorPopupPresenter = provider.get();
		idToPresenter.put(tutorId, tutorPopupPresenter);
		tutorPopupPresenter.init(tutorId);
		return tutorPopupPresenter;
	}
}
