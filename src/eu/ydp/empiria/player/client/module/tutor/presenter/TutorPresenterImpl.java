package eu.ydp.empiria.player.client.module.tutor.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.tutor.view.TutorView;

public class TutorPresenterImpl implements TutorPresenter {

	private final TutorView tutorView;

	@Inject
	public TutorPresenterImpl(TutorView tutorView) {
		this.tutorView = tutorView;
	}

	@Override
	public void init() {
		tutorView.bindUi();
	}

}
